package chenyu.jokes.presenter;

import android.os.Bundle;
import android.util.Log;
import chenyu.jokes.view.Joke.JokeFragment;
import chenyu.jokes.base.App;
import chenyu.jokes.model.Response;
import nucleus.presenter.RxPresenter;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

import static rx.android.schedulers.AndroidSchedulers.mainThread;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokePresenter extends RxPresenter<JokeFragment> {
  private int mPage = 1;
  //String sort = "desc";

  //int pagesize = 20;
  //String time = String.valueOf(System.currentTimeMillis()/1000);//"1418816972";

  @Override protected void onCreate(Bundle savedState){
    super.onCreate(savedState);
/*
    restartableFirst(1, new Func0<Observable<Response>>() {
      @Override public Observable<Response> call() {
        return App.getServerAPI().getJokes(mPage)
            .subscribeOn(io())
            .observeOn(mainThread())
            .subscribeOn(mainThread());
      },
          (mainActivity,response) -> mainActivity.on
    });
*/

    /*
    restartableLatestCache(1,
        () -> App.getServerAPI().getJokes(mPage).observeOn(mainThread()),
        (MainActivity activity, Response response) -> activity.onItemsNext(response.data.jokes),
        (MainActivity activity, Throwable throwable) -> activity.onItemsError(throwable));
   */

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
Log.d("Presenter: ","onCreate");
    request(1);
  }

  public void request(int page) {
    mPage = page;
    start(1);
  }
/*
  public void start(int mPage) {
    App.getServerAPI()
        .getJokes(mPage)
        .observeOn(mainThread())
        .compose(this.<Response>deliverLatestCache())
        .subscribe(new Action1<Observer<Response>> () {
          @Override public void call(Observer<Response> response) {
            getView().onItemsNext(response.data.jokes);
          }
        },new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            getView().onItemsError(throwable);
          }
        });
  }
  */

}
