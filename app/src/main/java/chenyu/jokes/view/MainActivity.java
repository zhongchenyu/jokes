package chenyu.jokes.view;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import chenyu.jokes.model.Joke;
import chenyu.jokes.presenter.MainPresenter;
import java.util.List;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainPresenter> {
  @BindView(R.id.recyclerView) public RecyclerView recyclerView;
  @BindView(R.id.refreshLayout) public SwipeRefreshLayout refreshLayout;
  @BindView(R.id.toolBar) public Toolbar toolbar;
  private JokeAdapter  jokeAdapter = new JokeAdapter();
  private int currentPage = 1;
  private int previousTotal = 0;
  private boolean loading = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    toolbar.setNavigationIcon(R.drawable.ic_32);
    toolbar.setTitle("");
    setSupportActionBar(toolbar);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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
    Log.d("After load", "count is "+jokeAdapter.getItemCount());
  }

  public void onItemsError(Throwable throwable) {
    Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT);
  }

  public void onLoadMore(){
    getPresenter().request(currentPage);
  }
}
