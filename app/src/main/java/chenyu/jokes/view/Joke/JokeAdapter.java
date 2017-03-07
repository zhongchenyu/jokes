package chenyu.jokes.view.Joke;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.model.Joke;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokeAdapter extends BaseScrollAdapter<Joke> {

  @BindView(R.id.content) public TextView content;
  @BindView(R.id.time) public TextView time;
  //private List<Joke> mJokes = new ArrayList<>();
/*
  public JokeAdapter(List<Joke> jokes) {
    mJokes = jokes;
  }
*/
  public JokeAdapter(){
    //mJokes = null;
  }

 @Override public int getLayout() {
   return R.layout.item_joke;
 }

  /*
  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(
        R.layout.item_joke,parent,false);
    ViewHolder holder = new ViewHolder(view);
    return holder;
  }
  */

  @Override public void onBindViewHolder(ViewHolder holder, int position){
   super.onBindViewHolder(holder,position);
    content.setText(mItems.get(position).getContent());
    time.setText(mItems.get(position).updatetime + " "+position);
  }
/*
  @Override public int getItemCount() {
    return mJokes.size();
  }

  public void addAll(List<Joke> jokes) {
    mJokes.addAll(jokes);
  }

  public void clear() {
    mJokes.clear();
  }
  */
/*
  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View view) {
      super(view);
    }
  }
*/
}
