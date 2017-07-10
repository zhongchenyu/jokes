package chenyu.jokes.feature.myCollection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollActivity;
import chenyu.jokes.database.JokeDataBaseAPI;
import chenyu.jokes.model.JokeCollection;
import chenyu.jokes.presenter.MyCollectionPresenter;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(MyCollectionPresenter.class)
public class MyCollectionActivity extends BaseScrollActivity<MyCollectionAdapter,MyCollectionPresenter, JokeCollection> {
  @BindView(R.id.toolBar) Toolbar toolbar;
  private ActionBar actionBar;

  public static void startActivity(Context context) {
    Intent intent = new Intent(context,MyCollectionActivity.class );
    context.startActivity(intent);
  }

  @Override public int getLayout() {
    return R.layout.activity_my_collection;
  }

  @Override public MyCollectionAdapter getAdapter() {
    return new MyCollectionAdapter();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    needLoadMore = false;
    initToolbar();
  }

  public void onCancelCollection(int position,int collectionId) {
    mAdapter.remove(position);
    mAdapter.notifyDataSetChanged();
    JokeDataBaseAPI.create().deleteJokeById(collectionId);
  }

  private void initToolbar() {
    toolbar.setNavigationIcon(R.drawable.ic_back_white_32);
    setSupportActionBar(toolbar);
    actionBar = getSupportActionBar();
    if(actionBar != null) {
      actionBar.setTitle("");
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
    }
  }
}
