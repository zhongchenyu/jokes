package chenyu.jokes.base;

import chenyu.jokes.app.AccountManager;
import nucleus.presenter.RxPresenter;

/**
 * Created by chenyu on 2017/3/7.
 */

public class BaseScrollPresenter<M> extends RxPresenter<M> {
  public void request(int page){

  }

  protected String getSendToken()
  {
    return "Bearer " + AccountManager.create().getToken();
  }
}
