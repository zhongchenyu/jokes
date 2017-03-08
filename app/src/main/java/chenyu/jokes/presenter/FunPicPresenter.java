package chenyu.jokes.presenter;

import android.os.Bundle;
import android.provider.Settings;
import chenyu.jokes.base.App;
import chenyu.jokes.base.BaseScrollPresenter;
import chenyu.jokes.model.FunPicResponse;
import chenyu.jokes.model.Response;
import chenyu.jokes.view.FunPic.FunPicFragment;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * Created by chenyu on 2017/3/7.
 */

public class FunPicPresenter extends BaseScrollPresenter<FunPicFragment>{
  private String key = "***REMOVED***";
  private String time = String.valueOf(System.currentTimeMillis()/1000);
  //http://***REMOVED***/joke/img/list.from?key=***REMOVED***&page=12&pagesize=20&sort=asc&time=1418745237
  private int mPage = 1;

  @Override protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);

    restartableFirst(1,
        new Func0<Observable<Response>>() {
          @Override public Observable<Response> call() {
            return App.getServerAPI()
                .getFunPic(key, "desc", mPage, 20, time)
                .subscribeOn(io())
                .observeOn(mainThread());
          }
        },
        new Action2<FunPicFragment, Response>() {
          @Override public void call(FunPicFragment funPicFragment, Response funPicResponse) {
            funPicFragment.onItemsNext(funPicResponse.result.data);
          }
        },
        new Action2<FunPicFragment, Throwable>() {
          @Override public void call(FunPicFragment funPicFragment, Throwable throwable) {
            funPicFragment.onItemsError(throwable);
          }
        }
    );

    request(1);
  }
  @Override public void request(int page) {
    mPage = page;
    start(1);
  }
}
