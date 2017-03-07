package chenyu.jokes.base;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import chenyu.jokes.model.Joke;
import chenyu.jokes.presenter.JokePresenter;
import chenyu.jokes.view.Joke.JokeAdapter;
import java.util.List;
import nucleus.factory.RequiresPresenter;
import nucleus.presenter.RxPresenter;
import nucleus.view.NucleusSupportFragment;
import org.parceler.Parcels;
/**
 * Created by chenyu on 2017/3/6.
 */

//@RequiresPresenter(JokePresenter.class)
public  class BaseScrollFragment<Adapter extends BaseScrollAdapter,P extends BaseScrollPresenter> extends NucleusSupportFragment<P>{

  @BindView(R.id.recyclerView) public RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) public SwipeRefreshLayout refreshLayout;
  //private JokeAdapter  jokeAdapter = new JokeAdapter();
  private int currentPage = 1;
  private int previousTotal = 0;
  private boolean loading = true;
  protected static final String  ADAPTER = "adapter";
  private Adapter mAdapter;

public void setAdapter(Adapter adapter) {
  mAdapter = adapter;
}
  public static BaseScrollFragment create(BaseScrollAdapter adapter) {
    BaseScrollFragment baseScrollFragment = new BaseScrollFragment();

    Bundle args = new Bundle();
    //args.put(ADAPTER, adapter);
    args.putParcelable(ADAPTER, Parcels.wrap(adapter));
    baseScrollFragment.setArguments(args);
    return baseScrollFragment;
  }

  //子类必须执行
  public int getLayout(){
    return 0;
  }
/*
@Override public void onCreate(Bundle state){
  super.onCreate(state);
  mAdapter = Parcels.unwrap(state.getParcelable(ADAPTER));
}*/

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
  View view = inflater.inflate(getLayout(), container, false);
  return view;
}

  @Override public void onViewCreated(View view,Bundle state) {
    super.onViewCreated(view,state);
    ButterKnife.bind(this,view);
  }

  @Override public void onResume() {
    super.onResume();
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    //mAdapter = new Adapter();
    recyclerView.setAdapter(mAdapter);

    refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        mAdapter.clear();
        getPresenter().start(1);
        currentPage = 1;
        previousTotal = 0;
        mAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        Log.d("After Refresh", "count is "+mAdapter.getItemCount());
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
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      return;
    }
    Log.d("After load", "count is "+mAdapter.getItemCount());
  }

  public void onItemsError(Throwable throwable) {
    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT);

  }

  public void onLoadMore(){
    getPresenter().request(currentPage);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mAdapter.clear();
  }
}
