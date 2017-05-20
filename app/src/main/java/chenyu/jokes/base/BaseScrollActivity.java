package chenyu.jokes.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import java.util.ArrayList;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Created by chenyu on 2017/5/15.
 */

public abstract class BaseScrollActivity<Adapter extends BaseScrollAdapter, P extends BaseScrollPresenter, M>
    extends NucleusAppCompatActivity<P> implements BaseRxView<M> {
  @BindView(R.id.recyclerView) public RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) public SwipeRefreshLayout refreshLayout;
  private int currentPage = 1;
  private int previousTotal = 0;
  private boolean loading = true;
  private boolean noMoreData = false;
  protected Adapter mAdapter;
  protected boolean needLoadMore = true;

  public abstract int getLayout();

  public abstract Adapter getAdapter();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayout());
    ButterKnife.bind(this);
    mAdapter = getAdapter();
    recyclerView.setAdapter(mAdapter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
  }

  @Override protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    initListener();
    getPresenter().loadPage(1);
  }

  private void initListener() {
    refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mAdapter.clear();
        getPresenter().loadPage(1);
        currentPage = 1;
        previousTotal = 0;
        mAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
      }
    });

    if (needLoadMore) {
      recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);

          if (noMoreData) {
            return;
          }

          //int visibleItemCount = recyclerView.getChildCount();
          int totalItemCount = recyclerView.getAdapter().getItemCount();
          int lastVisibleItem =
              ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
          if (loading) {
            if (totalItemCount > previousTotal) {
              loading = false;
              previousTotal = totalItemCount;
            }
          }
          if (!loading && lastVisibleItem >= totalItemCount - 1) {//(totalItemCount - visibleItemCount) <= firstVisibleItem

            loading = true;
            currentPage++;
            onLoadMore();
            previousTotal = totalItemCount;
          }
        }
      });
    }
  }

  @Override public void onItemsNext(ArrayList<M> items) {
    if (items.isEmpty()) {
      noMoreData = true;
      loading = false;
      return;
    }
    mAdapter.addAll(items);
    mAdapter.notifyDataSetChanged();
    loading = false;
  }

  @Override public void onItemsError(Throwable throwable) {
    Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
  }

  public void onLoadMore() {
    getPresenter().loadPage(currentPage);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mAdapter.clear();
  }
}
