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
  //private int mPage ;
  private int mJokeId;
  private int mPosition;
  private static final int GET_JOKES = 1;
  private static final int UP = 2;
  private static final int DOWN = 3;
  private static final int COLLECT = 4;
  private static final int COMMENT = 5;
  private static final int ATTITUDE_START = 100;
  private AttitudeType mAttitudeType ;
  private Func1 errorCodeProcess = new Func1<MyResponse, Observable<ArrayList<Data>>>() {
    @Override public Observable<ArrayList<Data>> call(MyResponse response) {
      //if(response.errorCode !=0) {
      //  return Observable.error(new Throwable(response.reason));
      //}
      return Observable.just(response.data);
    }
  };

  @Override protected void onCreate(Bundle savedState) {

    super.onCreate(savedState);
    /*
    restartableFirst(GET_JOKES,
        new Func0<Observable<ArrayList<Data>>>() {
          @Override public Observable<ArrayList<Data>> call() {
            return App.getServerAPI()
                .getJokes(getSendToken(), mPage)
                .subscribeOn(io())
                .observeOn(mainThread())
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
*/
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

    //loadPage(mPage);
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

  /*
    @Override public void request(int page) {
      mPage = page;
      start(GET_JOKES);
    }
  */
  public void attitude(int jokeId, int position, AttitudeType attitudeType) {
    mJokeId = jokeId;
    mPosition = position;
    mAttitudeType = attitudeType;
    start(ATTITUDE_START);
  }

  public void down(int jokeId, int position) {
    mJokeId = jokeId;
    mPosition = position;
    start(DOWN);
  }

  public void collect(int jokeId, int position) {
    mJokeId = jokeId;
    mPosition = position;
    start(COLLECT);
  }
}
