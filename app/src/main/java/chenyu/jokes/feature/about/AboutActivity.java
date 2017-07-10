package chenyu.jokes.feature.about;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;

public class AboutActivity extends AppCompatActivity {
  @BindView(R.id.version_name) TextView txtVersionName;
  @BindView(R.id.toolBar) Toolbar toolbar;
  private ActionBar actionBar;

  public static void startActivity(Context context) {
    Intent intent = new Intent(context, AboutActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    ButterKnife.bind(this);
    initToolbar();
    txtVersionName.setText(getVersionName(this));
  }

  public String getVersionName(Context context) {
    PackageManager packageManager = context.getPackageManager();
    PackageInfo packageInfo;
    String versionName = "";
    try {
      packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
      versionName = packageInfo.versionName;
    } catch (PackageManager.NameNotFoundException e) {
      e.printStackTrace();
    }
    return versionName;
  }

  private void initToolbar() {
    toolbar.setNavigationIcon(R.drawable.ic_back_white_32);
    setSupportActionBar(toolbar);
    actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setTitle("");
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
    }
  }
}
