package chenyu.jokes.presenter;

import android.os.Bundle;
import android.util.Log;
import chenyu.jokes.app.AccountManager;
import chenyu.jokes.app.App;
import chenyu.jokes.base.BaseScrollPresenter;
import chenyu.jokes.model.Data;
import chenyu.jokes.model.MyResponse;
import chenyu.jokes.model.Response;
import chenyu.jokes.feature.FunPic.FunPicFragment;
import java.util.ArrayList;
import java.util.Iterator;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * Created by chenyu on 2017/3/7.
 */

public class FunPicPresenter extends BaseScrollPresenter<FunPicFragment>{

  //private String time = String.valueOf(System.currentTimeMillis()/1000);

  private int mPage = 1;
  private ArrayList<String> mServerBlackList = new ArrayList<>();
  @Override protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);

    restartableFirst(0,
        new Func0<Observable<Response>>() {
          @Override public Observable<Response> call() {
            return App.getServerAPI().getBlackList().subscribeOn(io()).observeOn(mainThread());
          }
        },
        new Action2<FunPicFragment, Response>() {
          @Override
          public void call(FunPicFragment funPicFragment, Response response) {
            for (int i=0;i<response.result.data.size();i++) {
              mServerBlackList.add(response.result.data.get(i).hashId);
            }
            Log.d("FunPicPresenter", "mServerBlackList: " + mServerBlackList.toString());
          }
        },
        new Action2<FunPicFragment, Throwable>() {
          @Override public void call(FunPicFragment funPicFragment, Throwable throwable) {
            funPicFragment.onItemsError(throwable);
          }
        }
    );

        restartableFirst(1,
            new Func0<Observable<MyResponse>>() {
              @Override public Observable<MyResponse> call() {
                return App.getServerAPI()
                    .getFunPic("Bearer " + AccountManager.create().getToken(), mPage)
                    .subscribeOn(io())
                    .observeOn(mainThread());
              }
            },
            new Action2<FunPicFragment, MyResponse>() {
              @Override public void call(FunPicFragment funPicFragment, MyResponse funPicResponse) {
                Iterator<Data> iterable = funPicResponse.data.iterator();
                while (iterable.hasNext()) {
                  if(mServerBlackList.contains(iterable.next().hashId)) {
                    iterable.remove();
                  }
                }
                funPicFragment.onItemsNext(funPicResponse.data);
              }
            },
            new Action2<FunPicFragment, Throwable>() {
              @Override public void call(FunPicFragment funPicFragment, Throwable throwable) {
                funPicFragment.onItemsError(throwable);
              }
            }
        );
    start(0);
    request(1);
  }
  @Override public void request(int page) {
    mPage = page;
    start(1);
  }
}
