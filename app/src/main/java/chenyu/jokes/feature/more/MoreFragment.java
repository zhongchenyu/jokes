package chenyu.jokes.feature.more;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenyu.jokes.R;
import chenyu.jokes.app.AccountManager;
import chenyu.jokes.feature.about.AboutActivity;
import chenyu.jokes.feature.myCollection.MyCollectionActivity;
import chenyu.jokes.model.Account;
import chenyu.jokes.model.ErrorResponse;
import chenyu.jokes.model.Notice;
import chenyu.jokes.model.Token;
import chenyu.jokes.model.User;
import chenyu.jokes.presenter.MorePresenter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;
import retrofit2.HttpException;

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
  //@BindView(R.id.get_collection) Button mBtnCollection;
  @BindView(R.id.layout_collection) LinearLayout mLayoutCollection;
  @BindView(R.id.txt_collection) TextView mTxtCollection;
  @BindView(R.id.img_collection) ImageView mImgCollection;
  @BindView(R.id.layout_about) LinearLayout mLayoutAbout;

  private LocalBroadcastManager localBroadcastManager;

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
    mLayoutCollection.setClickable(false);
    localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
    if (!TextUtils.isEmpty(AccountManager.create().getToken())) {
      getPresenter().getUserInfo();
    }


    return view;
  }

  public void onRegisterSuccess(Token token) {
    Toast.makeText(getContext(), "注册成功，请登录", Toast.LENGTH_SHORT).show();
  }

  public void onLoginSuccess(Account account) {
    AccountManager.create().setAccount(account);
    mTxtName.setVisibility(View.VISIBLE);
    mTxtName.setText(account.user.name);
    mTxtEmail.setVisibility(View.VISIBLE);
    mTxtEmail.setText(account.user.email);
    mBtnLogin.setVisibility(View.INVISIBLE);
    mBtnLogout.setVisibility(View.VISIBLE);
    mBtnRegister.setVisibility(View.INVISIBLE);
    mLayoutCollection.setClickable(true);
    //mTxtCollection.setTextColor(ContextCompat.getColor(getContext(), R.color.baseColor));
    mImgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.baseColor));
    Intent intent = new Intent("chenyu.jokes.account.login");
    localBroadcastManager.sendBroadcast(intent);
  }

  public void onGetUserSuccess(User user) {
    AccountManager.create().setUser(user);
    mTxtName.setVisibility(View.VISIBLE);
    mTxtName.setText(user.name);
    mTxtEmail.setVisibility(View.VISIBLE);
    mTxtEmail.setText(user.email);
    mBtnLogin.setVisibility(View.INVISIBLE);
    mBtnLogout.setVisibility(View.VISIBLE);
    mBtnRegister.setVisibility(View.INVISIBLE);
    mLayoutCollection.setClickable(true);
    //mTxtCollection.setTextColor(ContextCompat.getColor(getContext(), R.color.baseColor));
    mImgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.baseColor));
  }

  public void onGetNoticeSuccess(Notice notice) {
    mTxtNotice.setText(notice.content);
  }

  public void onError(Throwable throwable) {
    if(throwable instanceof HttpException) {
      ObjectMapper objectMapper = new ObjectMapper();
      ErrorResponse errorResponse;
      HttpException exception = (HttpException) throwable;
      try {
          errorResponse = objectMapper.readValue(exception.response().errorBody().string(),
            ErrorResponse.class);
        Toast.makeText(getContext(), errorResponse.error, Toast.LENGTH_SHORT).show();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }


  }

  @OnClick({R.id.login, R.id.logout, R.id.register, R.id.notice, R.id.layout_about}) public void click(View view) {
    switch (view.getId()) {
      case R.id.login:
        showLoginDialog();
        break;
      case R.id.logout:
        AccountManager.create().clearAccount();

        mBtnLogin.setVisibility(View.VISIBLE);
        mBtnLogout.setVisibility(View.INVISIBLE);
        mBtnRegister.setVisibility(View.VISIBLE);
        //mBtnNotice.setEnabled(false);
        //mBtnCollection.setEnabled(false);
        mLayoutCollection.setClickable(false);
        //mTxtCollection.setTextColor(ContextCompat.getColor(getContext(), R.color.baseGrey));
        mImgCollection.setColorFilter(ContextCompat.getColor(getContext(), R.color.baseGrey));
        mTxtName.setVisibility(View.INVISIBLE);
        mTxtEmail.setVisibility(View.INVISIBLE);
        //mTxtNotice.setText("");

        Intent intent = new Intent("chenyu.jokes.account.logout");
        localBroadcastManager.sendBroadcast(intent);
        break;
      case R.id.register:
        showRegisterDialog();
        break;
      case R.id.notice:
        getPresenter().getNotice();
        break;
      case R.id.layout_about:
        AboutActivity.startActivity(getContext());
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
    final EditText edtPasswordConfirm = (EditText) view.findViewById(R.id.password_confirmation);

    builder.setPositiveButton("确定", null);

    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {

      }
    });

    final AlertDialog alertDialog = builder.create();
    alertDialog.show();
    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(
        new View.OnClickListener() {
          @Override public void onClick(View v) {
            String userName = edtUserName.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String password_confirm = edtPasswordConfirm.getText().toString().trim();
            if (!password.equals(password_confirm)) {
              Toast.makeText(getContext(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
              return;
            }
            getPresenter().register(userName, email, password);
            alertDialog.dismiss();
          }
        });
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
        getPresenter().login(email, password);
      }
    });

    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int whick) {

      }
    });

    builder.show();
  }

  @OnClick(R.id.layout_collection) public void getCollection(View view) {
    MyCollectionActivity.startActivity(getContext());
  }
}
