package chenyu.jokes.feature.FunPic;

import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollFragment;
import chenyu.jokes.model.Data;
import chenyu.jokes.presenter.FunPicPresenter;
import nucleus.factory.RequiresPresenter;

/**
 * Created by chenyu on 2017/3/7.
 */

@RequiresPresenter(FunPicPresenter.class)
public class FunPicFragment extends BaseScrollFragment<FunPicAdapter,FunPicPresenter, Data>{

  public static FunPicFragment create() {
    return new FunPicFragment();
  }

  @Override public FunPicAdapter getAdapter() {
    return new FunPicAdapter();
  }

  @Override public int getLayout() {
    return R.layout.fragment_fun_pic;
  }
}
