package chenyu.jokes.feature.more;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenyu.jokes.R;
import chenyu.jokes.app.App;
import chenyu.jokes.model.Account;
import chenyu.jokes.model.Notice;
import chenyu.jokes.model.Token;
import chenyu.jokes.presenter.MorePresenter;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;

@RequiresPresenter(MorePresenter.class)
public class MoreFragment extends NucleusSupportFragment<MorePresenter> {
  @BindView(R.id.avatar) ImageView mImgAvatar;
  @BindView(R.id.name) TextView mTxtName;
  @BindView(R.id.email) TextView mTxtEmail;
  @BindView(R.id.login) Button mBtnLogin;
  @BindView(R.id.logout) Button mBtnLogout;
  @BindView(R.id.register) Button mBtnRegister;
  @BindView(R.id.notice) Button mBtnNotice;
  @BindView(R.id.notice_content) TextView mTxtNotice;


  public static MoreFragment create() {
    return new MoreFragment();
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_more, container, false);
    ButterKnife.bind(this, view);
    return view;
  }

  public void onRegisterSuccess(Token token) {
    Toast.makeText(getContext(), "注册成功，请登录", Toast.LENGTH_SHORT).show();
  }
  public void onLoginSuccess(Account account) {
    App.TOKEN = account.token;
    App.USER_ID = account.user.id;
    mTxtName.setVisibility(View.VISIBLE);
    mTxtName.setText(account.user.name);
    mTxtEmail.setVisibility(View.VISIBLE);
    mTxtEmail.setText(account.user.email);
    mBtnLogin.setVisibility(View.INVISIBLE);
    mBtnLogout.setVisibility(View.VISIBLE);
    mBtnRegister.setVisibility(View.INVISIBLE);
    mBtnNotice.setVisibility(View.VISIBLE);
  }
  public void onGetNoticeSuccess(Notice notice) {
    mTxtNotice.setText(notice.content);
  }
  public void onError(Throwable throwable) {
    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
  }

  @OnClick({R.id.login, R.id.logout, R.id.register, R.id.notice}) public void click(View view) {
    switch (view.getId()) {
      case R.id.login:
        //Toast.makeText(getContext(), "login", Toast.LENGTH_SHORT).show();
        showLoginDialog();
        break;
      case R.id.logout:
        //Toast.makeText(getContext(), "logout", Toast.LENGTH_SHORT).show();
        App.USER_ID = "";
        App.TOKEN = "";
        mBtnLogin.setVisibility(View.VISIBLE);
        mBtnLogout.setVisibility(View.INVISIBLE);
        mBtnRegister.setVisibility(View.VISIBLE);
        mBtnNotice.setVisibility(View.INVISIBLE);
        mTxtName.setVisibility(View.INVISIBLE);
        mTxtEmail.setVisibility(View.INVISIBLE);
        mTxtNotice.setText("Notice");
        break;
      case R.id.register:
        Toast.makeText(getContext(), "register", Toast.LENGTH_SHORT).show();
        showRegisterDialog();
        break;
      case R.id.notice:
        getPresenter().getNotice();
        break;
    }
  }

  private void showRegisterDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setIcon(R.mipmap.ic_launcher).setTitle("注册");

    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_register, null);
    builder.setView(view);

    final EditText edtUserName = (EditText) view.findViewById(R.id.username);
    final EditText edtPassword = (EditText) view.findViewById(R.id.password);
    final EditText edtEmail = (EditText) view.findViewById(R.id.email);

    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        String userName = edtUserName.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        getPresenter().register(userName, email, password);
        //Toast.makeText(getContext(), a+" "+b, Toast.LENGTH_SHORT).show();
      }
    });

    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int whick) {

      }
    });

    builder.show();
  }

  private void showLoginDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    builder.setIcon(R.mipmap.ic_launcher).setTitle("登录");

    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_login, null);
    builder.setView(view);

    final EditText edtPassword = (EditText) view.findViewById(R.id.password);
    final EditText edtEmail = (EditText) view.findViewById(R.id.email);

    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        String password = edtPassword.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        getPresenter().login( email, password);
        //Toast.makeText(getContext(), a+" "+b, Toast.LENGTH_SHORT).show();
      }
    });

    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int whick) {

      }
    });

    builder.show();
  }
}
