package chenyu.jokes.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import java.util.ArrayList;

/**
 * Created by chenyu on 2017/3/3.
 */

public abstract class BaseScrollAdapter<Model, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {


  protected ArrayList<Model> mItems = new ArrayList<>();
  protected ViewGroup parent;


  public BaseScrollAdapter(){

  }

  public abstract int getLayout();

  @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(
        getLayout(),parent,false);
    this.parent = parent;
    return getViewHolder(view);
  }

  protected abstract VH getViewHolder(View view) ;
  @Override public void onBindViewHolder(VH holder, int position){
    ButterKnife.bind(this,holder.itemView);
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  public void addAll(ArrayList<Model> items) {
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

}
