package chenyu.jokes.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import java.util.List;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by chenyu on 2017/5/15.
 */

public class BaseScrollActivity<Adapter extends BaseScrollAdapter, P extends BaseScrollPresenter>
    extends NucleusAppCompatActivity<P> {
  @BindView(R.id.recyclerView) public RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) public SwipeRefreshLayout refreshLayout;
  private int currentPage = 1;
  private int previousTotal = 0;
  private boolean loading = true;
  private boolean noMoreData = false;
  protected Adapter mAdapter;
  protected boolean needLoadMore = true;

  public void setAdapter(Adapter adapter) {
    mAdapter = adapter;
  }

  //子类必须执行
  public int getLayout() {
    return 0;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    ButterKnife.bind(this);

    recyclerView.setAdapter(mAdapter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
  }

  @Override protected void onResume() {
    super.onResume();

    refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mAdapter.clear();
        getPresenter().request(1);
        currentPage = 1;
        previousTotal = 0;
        mAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
      }
    });

    if(needLoadMore) {
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);

          if (noMoreData) {
            return;
          }

          int visibleItemCount = recyclerView.getChildCount();
          int totalItemCount = recyclerView.getAdapter().getItemCount();
          int firstVisibleItem =
              ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

          if (loading) {
            if (totalItemCount > previousTotal) {
              loading = false;
              previousTotal = totalItemCount;
            }
          }
          if (!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem) {

            loading = true;
            currentPage++;
            onLoadMore();
            previousTotal = totalItemCount;
          }
        }
      });
    }

  }

  public void onItemsNext(List items) {
    if (items.isEmpty()) {
      noMoreData = true;
      loading = false;
      return;
    }
    mAdapter.addAll(items);
    mAdapter.notifyDataSetChanged();
    loading = false;
  }

  public void onItemsError(Throwable throwable) {
    Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    Log.d("onItemError", throwable.getMessage());
  }

  public void onLoadMore() {
    getPresenter().request(currentPage);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mAdapter.clear();
  }
}
