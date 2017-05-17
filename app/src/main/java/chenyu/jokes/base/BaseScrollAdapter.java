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

public abstract class BaseScrollAdapter<Model, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


  protected List<Model> mItems = new ArrayList<>();
  protected ViewGroup parent;

  public BaseScrollAdapter(List<Model> items) {
    mItems = items;
  }

  public BaseScrollAdapter(){

  }

  public  int getLayout(){
    return 0;
  }

  @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(
        getLayout(),parent,false);
    this.parent = parent;
    //ViewHolder holder = new ViewHolder(view);
    return getViewHolder(view);
  }

  protected abstract VH getViewHolder(View view) ;
  @Override public void onBindViewHolder(VH holder, int position){
    ButterKnife.bind(this,holder.itemView);
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  public void addAll(List<Model> items) {
    mItems.addAll(items);
  }

  public void add(Model item) {
    mItems.add(item);
  }
  public void clear() {
    mItems.clear();
  }

  public void remove(int index) {
    mItems.remove(index);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View view) {
      super(view);
    }
  }

}
