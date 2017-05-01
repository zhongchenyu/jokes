package chenyu.jokes.feature.more;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenyu.jokes.R;


public class MoreFragment extends Fragment {
  @BindView(R.id.avatar) ImageView mImgAvatar;
  @BindView(R.id.name) TextView mTxtName;
  @BindView(R.id.login) Button mBtnLogin;
  @BindView(R.id.logout) Button mBtnLogout;
  @BindView(R.id.register) Button mBtnRegister;


  public static MoreFragment create() {
    return new MoreFragment();
  }

  public static MoreFragment newInstance(String param1, String param2) {
    MoreFragment fragment = new MoreFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
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

  @OnClick({R.id.login, R.id.logout, R.id.register}) public void click(View view) {
    switch (view.getId()) {
      case R.id.login:
        Toast.makeText(getContext(), "login", Toast.LENGTH_SHORT).show();
        mBtnLogin.setVisibility(View.INVISIBLE);
        mBtnLogout.setVisibility(View.VISIBLE);
        mBtnRegister.setVisibility(View.INVISIBLE);
        mTxtName.setVisibility(View.VISIBLE);
        break;
      case R.id.logout:
        Toast.makeText(getContext(), "logout", Toast.LENGTH_SHORT).show();
        mBtnLogin.setVisibility(View.VISIBLE);
        mBtnLogout.setVisibility(View.INVISIBLE);
        mBtnRegister.setVisibility(View.VISIBLE);
        mTxtName.setVisibility(View.INVISIBLE);
        break;
      case R.id.register:
        Toast.makeText(getContext(), "register", Toast.LENGTH_SHORT).show();
        break;

    }
  }
}
