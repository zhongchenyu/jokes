package chenyu.jokes.feature.FunPic;

import android.os.Bundle;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollFragment;
import chenyu.jokes.presenter.FunPicPresenter;
import nucleus.factory.RequiresPresenter;

/**
 * Created by chenyu on 2017/3/7.
 */

@RequiresPresenter(FunPicPresenter.class)
public class FunPicFragment extends BaseScrollFragment<FunPicAdapter,FunPicPresenter>{
  public static FunPicFragment create() {
    FunPicFragment funPicFragment = new FunPicFragment();
    return funPicFragment;
  }

  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    setAdapter(new FunPicAdapter());
  }

  @Override public int getLayout() {
    return R.layout.fragment_fun_pic;
  }
}
