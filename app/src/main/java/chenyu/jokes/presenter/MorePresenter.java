package chenyu.jokes.presenter;

import android.os.Bundle;
import chenyu.jokes.app.AccountManager;
import chenyu.jokes.app.App;
import chenyu.jokes.feature.more.MoreFragment;
import chenyu.jokes.model.Account;
import chenyu.jokes.model.Notice;
import chenyu.jokes.model.Token;
import nucleus.presenter.RxPresenter;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;
/**
 * Created by chenyu on 2017/5/1.
 */

public class MorePresenter extends RxPresenter<MoreFragment> {
  private static final int REGISTER = 1;
  private static final int LOGIN = 2;
  private static final int NOTICE = 3;
  private String mName;
  private String mEmail;
  private String mPassword;

  @Override protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);

    restartableFirst(REGISTER,
        new Func0<Observable<Token>>() {
          @Override public Observable<Token> call() {
            return App.getServerAPI().register(mName, mEmail, mPassword)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                ;
          }
        },
        new Action2<MoreFragment, Token>() {
          @Override public void call(MoreFragment moreFragment, Token token) {
            moreFragment.onRegisterSuccess(token);
          }
        }, new Action2<MoreFragment, Throwable>() {
          @Override public void call(MoreFragment moreFragment, Throwable throwable) {
            moreFragment.onError(throwable);
          }
        }
    );

    restartableFirst(LOGIN,
        new Func0<Observable<Account>>() {
          @Override public Observable<Account> call() {
            return App.getServerAPI().login(mEmail, mPassword)
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
          }
        },
        new Action2<MoreFragment, Account>() {
          @Override public void call(MoreFragment moreFragment, Account account) {
            moreFragment.onLoginSuccess(account);
          }
        },
        new Action2<MoreFragment, Throwable>() {
          @Override public void call(MoreFragment moreFragment, Throwable throwable) {
            moreFragment.onError(throwable);
          }
        }
    );

    restartableFirst(NOTICE,
        new Func0<Observable<Notice>>() {
          @Override public Observable<Notice> call() {
            return App.getServerAPI().getNotice("Bearer " + AccountManager.create().getToken())
                .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
          }
        },
        new Action2<MoreFragment, Notice>() {
          @Override public void call(MoreFragment moreFragment, Notice notice) {
            moreFragment.onGetNoticeSuccess(notice);
          }
        },
        new Action2<MoreFragment, Throwable>() {
          @Override public void call(MoreFragment moreFragment, Throwable throwable) {
            moreFragment.onError(throwable);
          }
        }
    );
  }

  public void register(String name, String email, String password) {
    mName = name;
    mEmail = email;
    mPassword = password;
    start(REGISTER);
  }

  public void login(String email, String password) {
    mEmail = email;
    mPassword = password;
    start(LOGIN);
  }

  public void getNotice() {
    start(NOTICE);
  }
}
