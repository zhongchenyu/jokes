package chenyu.jokes.feature.Joke;

import android.widget.TextView;
import butterknife.BindView;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.model.Data;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokeAdapter extends BaseScrollAdapter<Data> {

  @BindView(R.id.content) public TextView content;
  @BindView(R.id.time) public TextView time;

 @Override public int getLayout() {
   return R.layout.item_joke;
 }

  @Override public void onBindViewHolder(ViewHolder holder, int position){
   super.onBindViewHolder(holder,position);
    content.setText(mItems.get(position).getContent());
    time.setText(mItems.get(position).time + " "+position);
  }

}
