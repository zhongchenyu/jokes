package chenyu.jokes.view;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import chenyu.jokes.R;
import chenyu.jokes.model.Joke;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokeAdapter extends RecyclerView.Adapter<JokeViewHolder> {
  private List<Joke> mJokes = new ArrayList<>();

  public JokeAdapter(List<Joke> jokes) {
    mJokes = jokes;
  }

  public JokeAdapter(){
    //mJokes = null;
  }


  @Override public JokeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(
        R.layout.item_joke,parent,false);
    JokeViewHolder holder = new JokeViewHolder(view);
    return holder;
  }

  @Override public void onBindViewHolder(JokeViewHolder holder, int position){
    holder.content.setText(mJokes.get(position).content);
    holder.time.setText(mJokes.get(position).updatetime);
  }

  @Override public int getItemCount() {
    return mJokes.size();
  }

  public void addAll(List<Joke> jokes) {
    mJokes.addAll(jokes);
  }
}
