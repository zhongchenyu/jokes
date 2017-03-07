package chenyu.jokes.presenter;

import android.os.Bundle;
import android.util.Log;
import chenyu.jokes.base.App;
import chenyu.jokes.base.BaseScrollPresenter;
import chenyu.jokes.model.Response;
import chenyu.jokes.view.Joke.JokeFragment;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokePresenter extends BaseScrollPresenter<JokeFragment> {
  private int mPage = 1;

  @Override protected void onCreate(Bundle savedState){
    super.onCreate(savedState);

    restartableFirst(1,
        new Func0<Observable<Response>>() {
          @Override public Observable<Response> call() {
            return App.getServerAPI().getJokes(mPage).observeOn(mainThread());
          }
        },
        new Action2<JokeFragment, Response>() {
          @Override public void call(JokeFragment jokeFragment, Response response) {
            Log.d("Presenter: ",response.data.jokes.size()+" ");
            jokeFragment.onItemsNext(response.data.jokes);
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
    start(1);
  }


}
