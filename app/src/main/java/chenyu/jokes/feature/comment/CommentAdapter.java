package chenyu.jokes.feature.comment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.feature.main.MainActivity;
import chenyu.jokes.model.Comment;

/**
 * Created by chenyu on 2017/5/15.
 */

public class CommentAdapter extends BaseScrollAdapter<Comment, CommentAdapter.CommentViewHolder>{

  @Override protected CommentViewHolder getViewHolder(View view) {
    return new CommentViewHolder(view);
  }

  @Override public int getLayout() {
    return R.layout.item_comment;
  }

  @Override public void onBindViewHolder(CommentViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.username.setText(mItems.get(position).user.name);
    holder.comment.setText(mItems.get(position).comment);
    holder.time.setText(mItems.get(position).getTime());
  }

  public class CommentViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.username) public TextView username;
    @BindView(R.id.comment) public TextView comment;
    @BindView(R.id.time) public TextView time;

    public CommentViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }
  }
}
