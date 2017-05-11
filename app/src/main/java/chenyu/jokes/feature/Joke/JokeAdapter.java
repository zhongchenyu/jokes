package chenyu.jokes.feature.Joke;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.feature.main.MainActivity;
import chenyu.jokes.model.Data;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;

/**
 * Created by chenyu on 2017/3/3.
 */

public class JokeAdapter extends BaseScrollAdapter<Data, JokeAdapter.JokeViewHolder> {

  @Override public int getLayout() {
    return R.layout.item_joke;
  }

  @Override protected JokeViewHolder getViewHolder(View view) {
    return new JokeViewHolder(view);
  }

  @Override public void onBindViewHolder(JokeViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);

    holder.content.setText(mItems.get(position).getContent());
    holder.time.setText(mItems.get(position).time);
    holder.joke = mItems.get(position).getContent().toString();
  }

  public static class JokeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.content) public TextView content;
    @BindView(R.id.time) public TextView time;
    @BindView(R.id.share) public TextView share;
    @BindView(R.id.share_icon) public ImageButton shareIcon;
    public String joke;

    public JokeViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    @OnClick({R.id.share, R.id.share_icon}) public void directShare(View view) {
      ((MainActivity) view.getContext()).getShareWindow()
          .setText(joke)
          .showAtLocation(view.getRootView(),
              Gravity.BOTTOM, 0, 0);
    }
  }
}
