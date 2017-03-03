package chenyu.jokes.view;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import chenyu.jokes.R;
import chenyu.jokes.model.Item;
import java.util.List;
import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokeAdapter extends RecyclerView.Adapter<JokeViewHolder> {
  private Item[] mJokes;

  public JokeAdapter(Item[] jokes) {
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
    Log.d("recycler",position+":  "+mJokes[position].content);
    holder.content.setText(mJokes[position].content);
    holder.time.setText(mJokes[position].updatetime);
  }

  @Override public int getItemCount() {
    return mJokes.length;
  }

  public void add(Item[] jokes) {
    mJokes = jokes;
    Log.d("recycler item length",String.valueOf(mJokes.length));
  }
}
