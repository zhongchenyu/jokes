package chenyu.jokes.feature.Joke;

import android.os.Bundle;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollFragment;
import chenyu.jokes.presenter.JokePresenter;
import nucleus.factory.RequiresPresenter;

/**
 * Created by chenyu on 2017/3/6.
 */

@RequiresPresenter(JokePresenter.class)
public class JokeFragment extends BaseScrollFragment<JokeAdapter,JokePresenter> {

  public static JokeFragment create() {
    JokeFragment jokeFragment = new JokeFragment();
    return jokeFragment;
  }

  @Override public void onCreate(Bundle state){
    super.onCreate(state);
    setAdapter(new JokeAdapter());
  }

@Override public int getLayout() {
  return R.layout.fragment_joke;
}
}
