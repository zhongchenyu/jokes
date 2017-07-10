package chenyu.jokes.feature.myCollection;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.model.JokeCollection;

/**
 * Created by chenyu on 2017/5/17.
 */

public class MyCollectionAdapter extends
    BaseScrollAdapter<JokeCollection, MyCollectionAdapter.CollectionViewHolder> {

  @Override protected CollectionViewHolder getViewHolder(View view){
    return new CollectionViewHolder(view);
  }

  @Override public int getLayout() {
    return R.layout.item_collection;
  }

  @Override public void onBindViewHolder(CollectionViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.content.setText(mItems.get(position).content);
  }

  public class CollectionViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.content) public TextView content;
    @BindView(R.id.cancel) public ImageButton cancel;

    public CollectionViewHolder(View view) {
      super(view);
      ButterKnife.bind(this, view);
    }

    @OnClick(R.id.cancel) public void cancel(View view) {
      ((MyCollectionActivity)view.getContext()).getPresenter().cancelCollection(
          mItems.get(getAdapterPosition()).jokeId,
          getAdapterPosition(),
          mItems.get(getAdapterPosition()).id);
      Toast.makeText(view.getContext(), "取消收藏", Toast.LENGTH_SHORT).show();
    }
  }
}
