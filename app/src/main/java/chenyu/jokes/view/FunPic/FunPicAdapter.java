package chenyu.jokes.view.FunPic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.model.Data;
import chenyu.jokes.model.FunPic;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.net.URL;

/**
 * Created by chenyu on 2017/3/7.
 */

public class FunPicAdapter extends BaseScrollAdapter<Data>{
  @BindView(R.id.content) public TextView content;
  @BindView(R.id.img) public ImageView img;

  @Override public int getLayout() {
    return R.layout.item_fun_pic;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position){
    super.onBindViewHolder(holder, position);
    content.setText(mItems.get(position).content);
    //img.setImageURI(mItems.get(position).getUri());

    //Picasso.with(holder.itemView.getContext()).load(mItems.get(position).getUri()).into(img);
    Glide.with(holder.itemView.getContext()).load(mItems.get(position).getUri()).into(img);
  }
}
