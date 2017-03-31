package chenyu.jokes.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import java.util.List;
import nucleus.view.NucleusSupportFragment;
/**
 * Created by chenyu on 2017/3/6.
 */

public  class BaseScrollFragment<Adapter extends BaseScrollAdapter,P extends BaseScrollPresenter> extends NucleusSupportFragment<P>{

  @BindView(R.id.recyclerView) public RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) public SwipeRefreshLayout refreshLayout;
  private int currentPage = 1;
  private int previousTotal = 0;
  private boolean loading = true;
  private Adapter mAdapter;

public void setAdapter(Adapter adapter) {
  mAdapter = adapter;
}

  //子类必须执行
  public int getLayout(){
    return 0;
  }

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
  View view = inflater.inflate(getLayout(), container, false);
  return view;
}

  @Override public void onViewCreated(View view,Bundle state) {
    super.onViewCreated(view,state);
    ButterKnife.bind(this,view);
    recyclerView.setAdapter(mAdapter);
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
  }

  @Override public void onResume() {
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

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getAdapter().getItemCount();
        int firstVisibleItem =( (LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if(loading) {
          if(totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
          }
        }
        if(!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem) {

          loading = true;
          currentPage ++;
          onLoadMore();
          previousTotal = totalItemCount;
        }
      }
    });
  }

  public void onItemsNext(List items) {
    mAdapter.addAll(items);
    mAdapter.notifyDataSetChanged();
    loading = false;
  }

  public void onItemsError(Throwable throwable) {
    Log.d("onItemError",throwable.getMessage());
  }

  public void onLoadMore(){
    getPresenter().request(currentPage);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mAdapter.clear();
  }
}
