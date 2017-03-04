package chenyu.jokes.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import chenyu.jokes.model.Joke;
import chenyu.jokes.presenter.MainPresenter;
import java.util.List;
import nucleus.manager.RequiresPresenter;
import nucleus.view.NucleusActivity;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusActivity<MainPresenter> {
  @BindView(R.id.listView) public ListView listView;
  @BindView(R.id.recyclerView) public RecyclerView recyclerView;
  private ArrayAdapter<Joke> listAdapter;
  private JokeAdapter  jokeAdapter = new JokeAdapter();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    listView.setAdapter(listAdapter = new ArrayAdapter<>(this, R.layout.item));

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(jokeAdapter);
  }

  public void onItemsNext(List<Joke> items) {
    listAdapter.clear();
    listAdapter.addAll(items);
    jokeAdapter.addAll(items);
  }

  public void onItemsError(Throwable throwable) {
    Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT);
  }
}
