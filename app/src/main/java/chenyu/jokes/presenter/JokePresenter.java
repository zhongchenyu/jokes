package chenyu.jokes.presenter;

import android.os.Bundle;
import chenyu.jokes.app.AccountManager;
import chenyu.jokes.app.App;
import chenyu.jokes.base.BaseScrollPresenter;
import chenyu.jokes.constant.AttitudeType;
import chenyu.jokes.feature.Joke.JokeFragment;
import chenyu.jokes.model.Data;
import chenyu.jokes.model.MyResponse;
import chenyu.jokes.model.Response;
import java.util.ArrayList;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func1;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;
import static rx.schedulers.Schedulers.trampoline;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokePresenter extends BaseScrollPresenter<JokeFragment, Data> {

  private int mJokeId;
  private int mPosition;

  private static final int ATTITUDE_START = 100;
  private AttitudeType mAttitudeType ;

  @Override protected void onCreate(Bundle savedState) {

    super.onCreate(savedState);

    restartableFirst(ATTITUDE_START,
        new Func0<Observable<MyResponse>>() {
          @Override public Observable<MyResponse> call() {
            return App.getServerAPI()
                .attitude(getSendToken(), mJokeId, mAttitudeType.getPath())
                .subscribeOn(io())
                .observeOn(mainThread());
          }
        },
        new Action2<JokeFragment, MyResponse>() {
          @Override public void call(JokeFragment jokeFragment, MyResponse myResponse) {
            jokeFragment.onAttitudeSuccess(mPosition, myResponse, mAttitudeType);
          }
        },
        new Action2<JokeFragment, Throwable>() {
          @Override public void call(JokeFragment jokeFragment, Throwable throwable) {
            jokeFragment.onItemsError(throwable);
          }
        }
    );

  }

  @Override protected Observable<ArrayList<Data>> loadPageRequest() {
    return App.getServerAPI()
        .getJokes(getSendToken(), mPage)
        .flatMap(new Func1<MyResponse, Observable<ArrayList<Data>>>() {
          @Override public Observable<ArrayList<Data>> call(MyResponse response) {
            return Observable.just(response.data);
          }
        });
  }

  public void attitude(int jokeId, int position, AttitudeType attitudeType) {
    mJokeId = jokeId;
    mPosition = position;
    mAttitudeType = attitudeType;
    start(ATTITUDE_START);
  }

}
