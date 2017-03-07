package chenyu.jokes.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyu on 2017/3/3.
 */

public abstract class BaseScrollAdapter<Model> extends RecyclerView.Adapter<BaseScrollAdapter.ViewHolder> {


  protected List<Model> mItems = new ArrayList<>();


  public BaseScrollAdapter(List<Model> items) {
    mItems = items;
  }

  public BaseScrollAdapter(){

  }


  public  int getLayout(){
    return 0;
  };

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(
        getLayout(),parent,false);
    ViewHolder holder = new ViewHolder(view);
    return holder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position){
    ButterKnife.bind(this,holder.itemView);
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  public void addAll(List<Model> items) {
    mItems.addAll(items);
  }

  public void clear() {
    mItems.clear();
  }


  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View view) {
      super(view);
    }
  }

}
