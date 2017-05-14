package chenyu.jokes.feature.Joke;

import android.os.Bundle;
import android.widget.Toast;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollFragment;
import chenyu.jokes.constant.AttitudeType;
import chenyu.jokes.model.MyResponse;
import chenyu.jokes.presenter.JokePresenter;
import nucleus.factory.RequiresPresenter;

/**
 * Created by chenyu on 2017/3/6.
 */

@RequiresPresenter(JokePresenter.class)
public class JokeFragment extends BaseScrollFragment<JokeAdapter, JokePresenter> {

  public static JokeFragment create() {
    JokeFragment jokeFragment = new JokeFragment();
    return jokeFragment;
  }

  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    setAdapter(new JokeAdapter());
  }

  @Override public int getLayout() {
    return R.layout.fragment_joke;
  }

  public void onAttitude(int jokeId, int position, AttitudeType attitudeType) {
    getPresenter().attitude(jokeId, position,attitudeType);
  }

  public void onDown(int jokeId, int position) {
    getPresenter().down(jokeId, position);
  }

  public void onCollect(int jokeId, int position) {
    getPresenter().collect(jokeId, position);
  }
  public void onAttitudeSuccess(int position, MyResponse response, AttitudeType attitudeType) {
    mAdapter.changeAttitude(position, attitudeType);
    mAdapter.notifyDataSetChanged();
    Toast.makeText(getContext(), response.message, Toast.LENGTH_SHORT).show();
  }
/*
  public void onDownSuccess(int position, MyResponse response) {
    mAdapter.changeAttitude(position, JokeAdapter.ACTION_DOWN);
    mAdapter.notifyDataSetChanged();
    Toast.makeText(getContext(), response.message, Toast.LENGTH_SHORT).show();
  }

  public void onCollectSuccess(int position, MyResponse response) {
    mAdapter.changeAttitude(position, JokeAdapter.ACTION_COLLECT);
    mAdapter.notifyDataSetChanged();
    Toast.makeText(getContext(), response.message, Toast.LENGTH_SHORT).show();
  }
  */
}
