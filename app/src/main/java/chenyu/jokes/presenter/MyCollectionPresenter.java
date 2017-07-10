package chenyu.jokes.presenter;

import android.os.Bundle;
import chenyu.jokes.app.App;
import chenyu.jokes.base.BaseScrollPresenter;
import chenyu.jokes.constant.AttitudeType;
import chenyu.jokes.database.JokeDataBaseAPI;
import chenyu.jokes.feature.myCollection.MyCollectionActivity;
import chenyu.jokes.model.JokeCollection;
import chenyu.jokes.model.MyResponse;
import java.util.ArrayList;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;
/**
 * Created by chenyu on 2017/5/17.
 */

public class MyCollectionPresenter extends BaseScrollPresenter<MyCollectionActivity, JokeCollection> {

  private int jokeId;
  private int position;
  private int collectionId;

  @Override protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);

    restartableFirst(2,
        new Func0<Observable<MyResponse>>() {
          @Override public Observable<MyResponse> call() {
            return App.getServerAPI()
                .attitude(getSendToken(), jokeId, AttitudeType.COLLECT.getPath())
                .subscribeOn(io()).observeOn(mainThread());
          }
        },
        new Action2<MyCollectionActivity, MyResponse>() {
          @Override
          public void call(MyCollectionActivity myCollectionActivity, MyResponse myResponse) {
            myCollectionActivity.onCancelCollection(position, collectionId);

          }
        },
        new Action2<MyCollectionActivity, Throwable>() {
          @Override
          public void call(MyCollectionActivity myCollectionActivity, Throwable throwable) {
            myCollectionActivity.onItemsError(throwable);
          }
        }
    );
  }

  @Override protected Observable<ArrayList<JokeCollection>> loadPageRequest() {
    return JokeDataBaseAPI.create().getJoke();
  }

  public void cancelCollection(int jokeId, int position, int collectionId) {
    this.jokeId = jokeId;
    this.collectionId = collectionId;
    this.position = position;
    start(2);
  }
}
