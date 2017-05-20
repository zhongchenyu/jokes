package chenyu.jokes.base;

import android.os.Bundle;
import chenyu.jokes.app.AccountManager;
import java.util.ArrayList;
import nucleus.presenter.RxPresenter;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * Created by chenyu on 2017/3/7.
 */

public abstract class BaseScrollPresenter<View extends BaseRxView, Model>
    extends RxPresenter<View> {
  protected int mPage;
  private final int INIT_LOAD = 1;

  @Override protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);

    restartableFirst(INIT_LOAD,
        new Func0<Observable<ArrayList<Model>>>() {
          @Override public Observable<ArrayList<Model>> call() {
            return loadPageRequest()
                .subscribeOn(io())
                .observeOn(mainThread());
          }
        },
        new Action2<View, ArrayList<Model>>() {
          @Override public void call(View view,
              ArrayList<Model> items) {
            view.onItemsNext(items);
          }
        },
        new Action2<View, Throwable>() {
          @Override public void call(View view, Throwable throwable) {
            view.onItemsError(throwable);
          }
        }
    );
  }

  protected abstract Observable<ArrayList<Model>> loadPageRequest();

  public void loadPage(int page) {
    mPage = page;
    start(INIT_LOAD);
  }

  protected String getSendToken() {
    return "Bearer " + AccountManager.create().getToken();
  }

}
