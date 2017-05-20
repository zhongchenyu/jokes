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
import rx.functions.Func1;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * Created by chenyu on 2017/3/7.
 */

public class FunPicPresenter extends BaseScrollPresenter<FunPicFragment, Data>{

  @Override protected Observable<ArrayList<Data>> loadPageRequest() {
    return App.getServerAPI()
        .getFunPic(getSendToken(), mPage)
        .flatMap(new Func1<MyResponse, Observable<ArrayList<Data>>>() {
          @Override public Observable<ArrayList<Data>> call(MyResponse myResponse) {
            return Observable.just(myResponse.data);
          }
        });
  }
}
