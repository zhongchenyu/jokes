package chenyu.jokes.feature.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import chenyu.jokes.feature.FunPic.FunPicFragment;
import chenyu.jokes.feature.Joke.JokeFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import nucleus.view.NucleusAppCompatActivity;


public class MainActivity extends NucleusAppCompatActivity {

  @BindView(R.id.mainContent) FrameLayout mainContent;
  @BindView(R.id.toolBar) public Toolbar toolbar;
  @BindView(R.id.bottomBar) public BottomBar mBottomBar;
  private FragmentManager fragmentManager;

  JokeFragment jokeFragment = JokeFragment.create();
  FunPicFragment funPicFragment = FunPicFragment.create();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    toolbar.setNavigationIcon(R.drawable.ic_32);
    toolbar.setTitle("");
    setSupportActionBar(toolbar);

    fragmentManager = getSupportFragmentManager();

    addFragment(funPicFragment);
    hideFragment(funPicFragment);
    addFragment(jokeFragment);

    mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
      @Override
      public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
          case R.id.tabJoke:
            showFragment(jokeFragment);
            hideFragment(funPicFragment);
            break;
          case R.id.tabFunPic:
            showFragment(funPicFragment);
            hideFragment(jokeFragment);
            break;
        }
      }
    });


  }


  private void replaceFragment(Fragment fragment) {
    fragmentManager.beginTransaction().replace(R.id.mainContent, fragment).commit();
  }

  private void addFragment(Fragment fragment) {
    fragmentManager.beginTransaction().add(R.id.mainContent,fragment).commit();
  }

  private void showFragment(Fragment fragment) {
    fragmentManager.beginTransaction().show(fragment).commit();
  }
  private void hideFragment(Fragment fragment) {
    fragmentManager.beginTransaction().hide(fragment).commit();
  }
}
