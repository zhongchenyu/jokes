package chenyu.jokes.feature.MyCollection;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollActivity;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.database.JokeDataBaseAPI;
import chenyu.jokes.feature.comment.JokeCommentActivity;
import chenyu.jokes.model.Data;
import chenyu.jokes.presenter.MyCollectionPresenter;
import nucleus.factory.RequiresPresenter;
import org.parceler.Parcels;

@RequiresPresenter(MyCollectionPresenter.class)
public class MyCollectionActivity extends BaseScrollActivity<MyCollectionAdapter,MyCollectionPresenter> {

  public static void startActivity(Context context) {
    Intent intent = new Intent(context,MyCollectionActivity.class );
    context.startActivity(intent);
  }

  @Override public int getLayout() {
    return R.layout.activity_my_collection;
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setAdapter(new MyCollectionAdapter());

    super.onCreate(savedInstanceState);
    needLoadMore = false;
    getPresenter().request();
  }

  public void onCancelCollection(int position,int collectionId) {
    mAdapter.remove(position);
    mAdapter.notifyDataSetChanged();
    JokeDataBaseAPI.create().deleteJokeById(collectionId);
  }
}
