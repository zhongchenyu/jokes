package chenyu.jokes.presenter;

import android.os.Bundle;
import chenyu.jokes.app.App;
import chenyu.jokes.base.BaseScrollPresenter;
import chenyu.jokes.model.Response;
import chenyu.jokes.feature.Joke.JokeFragment;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

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
        new Func0<Observable<Response>>() {
          @Override public Observable<Response> call() {
            return App.getServerAPI().getJokes(mPage).subscribeOn(io()).observeOn(mainThread());
          }
        },
        new Action2<JokeFragment, Response>() {
          @Override public void call(JokeFragment jokeFragment, Response response) {
            jokeFragment.onItemsNext(response.result.data);
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
}
