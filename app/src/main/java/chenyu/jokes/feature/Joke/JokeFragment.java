package chenyu.jokes.feature.Joke;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollFragment;
import chenyu.jokes.constant.AttitudeType;
import chenyu.jokes.model.Data;
import chenyu.jokes.model.MyResponse;
import chenyu.jokes.presenter.JokePresenter;
import nucleus.factory.RequiresPresenter;

/**
 * Created by chenyu on 2017/3/6.
 */

@RequiresPresenter(JokePresenter.class)
public class JokeFragment extends BaseScrollFragment<JokeAdapter, JokePresenter, Data> {
  LocalBroadcastManager localBroadcastManager;
  IntentFilter intentFilter;
  LocalReceiver localReceiver;

  public static JokeFragment create() {
    return new JokeFragment();
  }

  @Override public JokeAdapter getAdapter() {
    return new JokeAdapter();
  }

  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    intentFilter = new IntentFilter();
    intentFilter.addAction("chenyu.jokes.account.logout");
    intentFilter.addAction("chenyu.jokes.account.login");
    localReceiver = new LocalReceiver();
    localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
    localBroadcastManager.registerReceiver(localReceiver, intentFilter);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    localBroadcastManager.unregisterReceiver(localReceiver);
  }

  @Override public int getLayout() {
    return R.layout.fragment_joke;
  }

  public void onAttitude(int jokeId, int position, AttitudeType attitudeType) {
    getPresenter().attitude(jokeId, position,attitudeType);
  }
  public void onAttitudeSuccess(int position, MyResponse response, AttitudeType attitudeType) {
    mAdapter.changeAttitude(position, attitudeType);
    mAdapter.notifyDataSetChanged();
  }

  class LocalReceiver extends BroadcastReceiver{
    @Override public void onReceive(Context context, Intent intent) {
      listener.onRefresh();
    }
  }
}
