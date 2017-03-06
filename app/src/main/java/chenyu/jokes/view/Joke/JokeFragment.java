package chenyu.jokes.view.Joke;

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
import java.util.List;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusSupportFragment;

/**
 * Created by chenyu on 2017/3/6.
 */

@RequiresPresenter(JokePresenter.class)
public class JokeFragment extends NucleusSupportFragment<JokePresenter>{

  @BindView(R.id.recyclerView) public RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) public SwipeRefreshLayout refreshLayout;
  private JokeAdapter  jokeAdapter = new JokeAdapter();
  private int currentPage = 1;
  private int previousTotal = 0;
  private boolean loading = true;

  public static JokeFragment create() {
    JokeFragment jokeFragment = new JokeFragment();
    //Bundle args = new Bundle();
    //args.putString(ARGUMENT_ID, id);
    //jokeFragment.setArguments(args);
    return jokeFragment;
  }

@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
    Bundle savedInstanceState) {
  View view = inflater.inflate(R.layout.fragment_joke, container, false);
  Toast.makeText(getContext(),"onCreateView",Toast.LENGTH_SHORT);
  Log.d("Fragment","onCreateView");
  return view;
}

  @Override public void onViewCreated(View view,Bundle state) {
    super.onViewCreated(view,state);
    ButterKnife.bind(this,view);
    Toast.makeText(getContext(),"onViewCreated",Toast.LENGTH_SHORT);
    Log.d("Fragment","ononViewCreated");
  }

  @Override public void onResume() {
    super.onResume();
Toast.makeText(getContext(),"onResume",Toast.LENGTH_SHORT);
    Log.d("Fragment","onResume");
    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(jokeAdapter);

    refreshLayout.setColorSchemeResources(R.color.colorPrimary);
    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        jokeAdapter.clear();
        getPresenter().start(1);
        currentPage = 1;
        previousTotal = 0;
        jokeAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        Log.d("After Refresh", "count is "+jokeAdapter.getItemCount());
      }
    });

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getAdapter().getItemCount();
        int firstVisibleItem =( (LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        //Log.d("Scroll_if1","Loading="+loading+", "+"total="+totalItemCount+", "+"previous="+previousTotal);
        if(loading) {
          if(totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
          }
        }
        //Log.d("Scroll_if2","Loading="+loading+", "+"total="+totalItemCount+", "+"previous="+previousTotal);
        if(!loading && (totalItemCount - visibleItemCount) <= firstVisibleItem) {

          loading = true;
          currentPage ++;
          onLoadMore();
          previousTotal = totalItemCount;
          //  Log.d("scroll","to the end, loading "+currentPage);
        }
      }
    });
  }

  public void onItemsNext(List<Joke> items) {
    jokeAdapter.addAll(items);
    jokeAdapter.notifyDataSetChanged();
    loading = false;
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      return;
    }
    Log.d("After load", "count is "+jokeAdapter.getItemCount());
  }

  public void onItemsError(Throwable throwable) {
    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT);

  }

  public void onLoadMore(){
    Toast.makeText(getContext(),"onLoadMore",Toast.LENGTH_SHORT);
    getPresenter().request(currentPage);
  }

  @Override public void onPause() {
    super.onPause();
    Toast.makeText(getContext(),"onPause",Toast.LENGTH_SHORT);
    Log.d("Fragment","onPause");
  }
  @Override public void onStop(){
    super.onStop();
    Toast.makeText(getContext(),"onStop",Toast.LENGTH_SHORT);
    Log.d("Fragment","onStop");
  }
  @Override public void onDestroyView() {
    super.onDestroyView();
    jokeAdapter.clear();
    Toast.makeText(getContext(),"onDestroyView",Toast.LENGTH_SHORT);
    Log.d("Fragment","onDestroyView");
  }
}
