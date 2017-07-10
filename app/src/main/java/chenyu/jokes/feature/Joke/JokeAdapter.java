package chenyu.jokes.feature.Joke;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenyu.jokes.R;
import chenyu.jokes.app.AccountManager;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.constant.AttitudeType;
import chenyu.jokes.database.JokeDataBaseAPI;
import chenyu.jokes.feature.comment.JokeCommentActivity;
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
    holder.time.setText(mItems.get(position).getTime());
    holder.joke = mItems.get(position).getContent().toString();
    holder.jokeId = mItems.get(position).id;

    holder.txtUp.setText(String.valueOf(mItems.get(position).up_amount));
    holder.txtDown.setText(String.valueOf(mItems.get(position).down_amount));
    holder.txtComment.setText(String.valueOf(mItems.get(position).comment_amount));
    holder.txtCollect.setText(String.valueOf(mItems.get(position).collect_amount));
    switch (mItems.get(position).my_attitude) {
      case 0:
        holder.txtUp.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
        holder.txtDown.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
        holder.imgUp.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
        holder.imgDown.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
        break;
      case 1:
        holder.txtUp.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseColor));
        holder.txtDown.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
        holder.imgUp.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseColor));
        holder.imgDown.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
        break;
      case -1:
        holder.txtUp.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
        holder.txtDown.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseColor));
        holder.imgUp.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
        holder.imgDown.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseColor));
        break;
    }
    if (mItems.get(position).my_collected) {
      holder.txtCollect.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseColor));
      //holder.imgCollect.setImageDrawable(ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_star_p));
      holder.imgCollect.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseColor));
    } else {
      holder.txtCollect.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
      //holder.imgCollect.setImageDrawable(ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_star_n));
      holder.imgCollect.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
    }

    if(mItems.get(position).comment_amount == 0) {
      holder.imgComment.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
      holder.txtComment.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseGrey));
    } else {
      holder.imgComment.setColorFilter(ContextCompat.getColor(parent.getContext(), R.color.baseColor));
      holder.txtComment.setTextColor(ContextCompat.getColor(parent.getContext(), R.color.baseColor));
    }
  }

  public  class JokeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.content) public TextView content;
    @BindView(R.id.time) public TextView time;
    @BindView(R.id.share_icon) public ImageButton shareIcon;
    @BindView(R.id.up) public TextView txtUp;
    @BindView(R.id.down) public TextView txtDown;
    @BindView(R.id.comment) public TextView txtComment;
    @BindView(R.id.collect) public TextView txtCollect;
    @BindView(R.id.ic_down) public ImageView imgDown;
    @BindView(R.id.ic_collect) public ImageView imgCollect;
    @BindView(R.id.ic_up) public ImageView imgUp;
    @BindView(R.id.ic_comment) public ImageView imgComment;

    public String joke;
    private int jokeId;
    //private int position;
    private JokeFragment jokeFragment;

    public JokeViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
      jokeFragment = ((MainActivity)parent.getContext()).getJokeFragment();
    }

    @OnClick(R.id.share_icon) public void directShare(View view) {
      ((MainActivity) view.getContext()).getShareWindow()
          .setText(joke)
          .showAtLocation(view.getRootView(),
              Gravity.BOTTOM, 0, 0);
    }

    @OnClick({R.id.up, R.id.down, R.id.comment, R.id.collect, R.id.ic_up, R.id.ic_down, R.id.ic_collect, R.id.ic_comment})
    public void onClick(View view) {
      if(TextUtils.isEmpty(AccountManager.create().getToken())) {
        Toast.makeText(itemView.getContext(), "需要先登录", Toast.LENGTH_SHORT).show();
        return;
      }
      switch (view.getId()) {
        case R.id.up:
        case R.id.ic_up:
          jokeFragment.onAttitude(jokeId, getAdapterPosition(), AttitudeType.UP);
          break;

        case R.id.down:
        case R.id.ic_down:
          jokeFragment.onAttitude(jokeId, getAdapterPosition(), AttitudeType.DOWN);
          break;

        case R.id.comment:
        case R.id.ic_comment:
          JokeCommentActivity.startActivity(view.getContext(), mItems.get(getAdapterPosition()));
          break;

        case R.id.collect:
        case R.id.ic_collect:
          jokeFragment.onAttitude(jokeId, getAdapterPosition(),AttitudeType.COLLECT);
          break;
      }
    }

  }

  public void changeAttitude(int position, AttitudeType attitudeType) {
    switch (attitudeType) {
      case UP:
        toggleUp(position);
        break;
      case DOWN:
        toggleDown(position);
        break;
      case COLLECT:
        toggleCollect(position);
        break;
    }
  }


  private void toggleUp(int position) {
    switch (mItems.get(position).my_attitude) {
      case 1:
        mItems.get(position).up_amount--;
        mItems.get(position).my_attitude = 0;
        break;
      case 0:
        mItems.get(position).up_amount++;
        mItems.get(position).my_attitude = 1;
        break;
      case -1:
        mItems.get(position).up_amount++;
        mItems.get(position).down_amount --;
        mItems.get(position).my_attitude = 1;
        break;
    }
  }

  private void toggleDown(int position) {
    switch (mItems.get(position).my_attitude) {
      case 1:
        mItems.get(position).up_amount--;
        mItems.get(position).down_amount ++;
        mItems.get(position).my_attitude = -1;
        break;
      case 0:
        mItems.get(position).down_amount++;
        mItems.get(position).my_attitude = -1;
        break;
      case -1:
        mItems.get(position).down_amount --;
        mItems.get(position).my_attitude = 0;
        break;
    }
  }
  private void toggleCollect(int position) {
    if (mItems.get(position).my_collected) {//取消收藏
      mItems.get(position).my_collected = false;
      mItems.get(position).collect_amount --;
      JokeDataBaseAPI.create().deleteJokeByJokeId(mItems.get(position).id);
    } else { //添加收藏
      mItems.get(position).my_collected = true;
      mItems.get(position).collect_amount ++;
      JokeDataBaseAPI.create().insertJoke(mItems.get(position));
    }
  }
}
