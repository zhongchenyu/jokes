package chenyu.jokes.presenter;

import android.os.Bundle;
import chenyu.jokes.app.App;
import chenyu.jokes.base.BaseScrollPresenter;
import chenyu.jokes.feature.Joke.JokeFragment;
import chenyu.jokes.model.Data;
import chenyu.jokes.model.Response;
import java.util.ArrayList;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokePresenter extends BaseScrollPresenter<JokeFragment> {
  private int mPage = 1;
  public static final int GET_JOKES = 1;
  @Override protected void onCreate(Bundle savedState){


    super.onCreate(savedState);
    restartableFirst(GET_JOKES,
        new Func0<Observable<ArrayList<Data>>>() {
          @Override public Observable<ArrayList<Data>> call() {
            return App.getServerAPI().getJokes(mPage).subscribeOn(io()).observeOn(mainThread())
                .flatMap(errorCodeProcess);
          }
        },
        new Action2<JokeFragment, ArrayList<Data>>() {
          @Override public void call(JokeFragment jokeFragment, ArrayList<Data> data) {
            jokeFragment.onItemsNext(data);
          }
        },
        new Action2<JokeFragment, Throwable>() {
          @Override public void call(JokeFragment jokeFragment, Throwable throwable) {
            jokeFragment.onItemsError(throwable);
          }
        }
    );
    request(1);
  }

  @Override  public void request(int page) {
    mPage = page;
    start(GET_JOKES);
  }

  private Func1 errorCodeProcess = new Func1<Response, Observable<ArrayList<Data>>>() {
    @Override public Observable<ArrayList<Data>> call(Response response) {
      if(response.errorCode !=0) {
        return Observable.error(new Throwable(response.reason));
      }
      return Observable.just(response.result.data);
    }
  };
}
