package chenyu.jokes.presenter;

import android.os.Bundle;
import chenyu.jokes.view.MainActivity;
import chenyu.jokes.base.App;
import chenyu.jokes.model.Response;
import chenyu.jokes.base.ServerAPI;
import nucleus.presenter.RxPresenter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by chenyu on 2017/3/3.
 */

public class MainPresenter extends RxPresenter<MainActivity> {
  String api_key = ServerAPI.API_KEY;
  String sort = "desc";
  int page = 1;
  int pagesize = 20;
  String time = String.valueOf(System.currentTimeMillis()/1000);//"1418816972";

  @Override protected void onCreate(Bundle savedState){
    super.onCreate(savedState);
    App.getServerAPI()
        .getJokes(api_key, sort,page,pagesize,time)
        .observeOn(AndroidSchedulers.mainThread())
        .compose(this.<Response>deliverLatestCache())
        .subscribe(new Action1<Response> () {
          @Override public void call(Response response) {
            getView().onItemsNext(response.data.jokes);
          }
        },new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            getView().onItemsError(throwable);
          }
        });
  }
}
