package chenyu.jokes.view.Joke;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokeViewHolder extends RecyclerView.ViewHolder {
  @BindView(R.id.content) public TextView content;
  @BindView(R.id.time) public TextView time;

  public JokeViewHolder(View view) { //Context context, ViewGroup parent
    super(view);
    ButterKnife.bind(this,itemView);
  }
}
