package chenyu.jokes.app;

import android.content.SharedPreferences;
import chenyu.jokes.model.Account;
import chenyu.jokes.model.User;

/**
 * Created by chenyu on 2017/5/2.
 */

public class AccountManager {
  private static SharedPreferences  sp;
  private static SharedPreferences.Editor editor;

  public static AccountManager create() {
    AccountManager accountManager = new AccountManager();
    accountManager.sp = App.getAppContext().getSharedPreferences("account", 0);
    accountManager.editor = sp.edit();
    return accountManager;
  }

  public void setToken(String token) {
    editor.putString("token", token);
    editor.commit();
  }

  public String getToken() {
    String token = sp.getString("token", "");
    return token;
  }

  public void setAccount(Account account) {
    editor.putString("token", account.token);
    editor.putString("userId", account.user.id);
    editor.putString("userEmail", account.user.email);
    editor.putString("userName", account.user.name);
    editor.commit();
  }

  public Account getAccount() {
    Account account = new Account();
    account.user = new User();
    account.token = sp.getString("token", "");
    account.user.id = sp.getString("userId", "");
    account.user.name = sp.getString("userEmail", "");
    account.user.email = sp.getString("userEmail", "");
    return account;
  }

  public void clearAccount() {
    editor.putString("token", "");
    editor.putString("userId", "");
    editor.putString("userEmail", "");
    editor.putString("userName", "");
    editor.commit();
  }

  public void setUser(User user) {
    editor.putString("userId", user.id);
    editor.putString("userEmail", user.email);
    editor.putString("userName", user.name);
    editor.commit();
  }
}
