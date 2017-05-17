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
import chenyu.jokes.model.MyResponse;
import chenyu.jokes.presenter.JokePresenter;
import nucleus.factory.RequiresPresenter;

/**
 * Created by chenyu on 2017/3/6.
 */

@RequiresPresenter(JokePresenter.class)
public class JokeFragment extends BaseScrollFragment<JokeAdapter, JokePresenter> {
  LocalBroadcastManager localBroadcastManager;
  IntentFilter intentFilter;
  LocalReceiver localReceiver;

  public static JokeFragment create() {
    JokeFragment jokeFragment = new JokeFragment();
    return jokeFragment;
  }

  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    setAdapter(new JokeAdapter());
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

  class LocalReceiver extends BroadcastReceiver{
    @Override public void onReceive(Context context, Intent intent) {
      listener.onRefresh();
      //Toast.makeText(context, "收到广播", Toast.LENGTH_SHORT).show();
    }
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
