package chenyu.jokes.feature.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import chenyu.jokes.feature.main.MainActivity;
import chenyu.jokes.util.RSAUtil;

public class SplashActivity extends AppCompatActivity {
  @BindView(R.id.logo) ImageView mImgLogo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);
    ButterKnife.bind(this);

    Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_drop_anim);
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {

      }

      @Override public void onAnimationEnd(Animation animation) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
            Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    mImgLogo.setAnimation(animation);
  }

  private void startMainActivity() {

  }
}
