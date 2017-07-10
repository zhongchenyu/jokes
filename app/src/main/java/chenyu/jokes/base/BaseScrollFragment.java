package chenyu.jokes.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import java.util.ArrayList;
import nucleus.view.NucleusSupportFragment;

/**
 * Created by chenyu on 2017/3/6.
 */

public abstract class BaseScrollFragment<Adapter extends BaseScrollAdapter, P extends BaseScrollPresenter, M>
    extends NucleusSupportFragment<P> implements BaseRxView<M> {

  @BindView(R.id.recyclerView) public RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) public SwipeRefreshLayout refreshLayout;
  private int currentPage = 1;
  private int previousTotal = 0;
  private boolean loading = true;
  private boolean noMoreData = false;
  protected Adapter mAdapter;
  protected SwipeRefreshLayout.OnRefreshListener listener;

  public abstract int getLayout();

  public abstract Adapter getAdapter();

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(getLayout(), container, false);
    return view;
  }

  @Override public void onViewCreated(View view, Bundle state) {
    super.onViewCreated(view, state);
    ButterKnife.bind(this, view);
    mAdapter = getAdapter();
    recyclerView.setAdapter(mAdapter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    initListener();
    getPresenter().loadPage(1);
  }

  private void initListener() {
    refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    listener = new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mAdapter.clear();
        getPresenter().loadPage(1);
        currentPage = 1;
        previousTotal = 0;
        mAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
      }
    };
    refreshLayout.setOnRefreshListener(listener);

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
        if (!loading && lastVisibleItem >= totalItemCount - 1) {

          loading = true;
          currentPage++;
          onLoadMore();
          previousTotal = totalItemCount;
        }
      }
    });
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
    Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
  }

  public void onLoadMore() {
    getPresenter().loadPage(currentPage);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mAdapter.clear();
  }
}
