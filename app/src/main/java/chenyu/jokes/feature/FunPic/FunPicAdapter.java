package chenyu.jokes.feature.FunPic;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollAdapter;
import chenyu.jokes.model.Data;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

/**
 * Created by chenyu on 2017/3/7.
 */

public class FunPicAdapter extends BaseScrollAdapter<Data, FunPicAdapter.FunPicViewHolder>{


  @Override public int getLayout() {
    return R.layout.item_fun_pic;
  }

  @Override protected FunPicViewHolder getViewHolder(View view) {
    return new FunPicViewHolder(view);
  }
  @Override public void onBindViewHolder(FunPicViewHolder holder, int position){
    super.onBindViewHolder(holder, position);
    holder.content.setText(mItems.get(position).getContent());

    Uri uri = mItems.get(position).getUri();
    if (mItems.get(position).url.endsWith(".gif")) {
      Glide.with(holder.itemView.getContext()).load(uri).diskCacheStrategy(
          DiskCacheStrategy.SOURCE)
          .listener(new RequestListener<Uri, GlideDrawable>() {
            @Override public boolean onException(Exception e, Uri model, Target<GlideDrawable> target,
                boolean isFirstResource) {
              Log.w("Glide in FunPicAdapter","onException: ",e);
              Log.d("Glide in FunPicAdapter","onException: "+model);
              Log.d("Glide in FunPicAdapter","onException: "+target.getRequest().isRunning());
              return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, Uri model,
                Target<GlideDrawable> target,
                boolean isFromMemoryCache, boolean isFirstResource) {
              Log.d("Glide in FunPicAdapter","onResourceReady: "+model);
              Log.d("Glide in FunPicAdapter","onResourceReady, thread: "+android.os.Process.myTid()+Thread.currentThread().getName());
              return false;
            }
          })
          .into(holder.img);
    } else {
      Picasso.with(holder.itemView.getContext()).load(uri).into(holder.img);
    }



    /*
    Glide.with(holder.itemView.getContext()).load(mItems.get(position).getUri()).diskCacheStrategy(
        DiskCacheStrategy.SOURCE)
        .listener(new RequestListener<Uri, GlideDrawable>() {
          @Override public boolean onException(Exception e, Uri model, Target<GlideDrawable> target,
              boolean isFirstResource) {
            Log.w("Glide in FunPicAdapter","onException: ",e);
            Log.d("Glide in FunPicAdapter","onException: "+model);
            Log.d("Glide in FunPicAdapter","onException: "+target.getRequest().isRunning());
            return false;
          }

          @Override
          public boolean onResourceReady(GlideDrawable resource, Uri model,
              Target<GlideDrawable> target,
              boolean isFromMemoryCache, boolean isFirstResource) {
            Log.d("Glide in FunPicAdapter","onResourceReady: "+model);
            Log.d("Glide in FunPicAdapter","onResourceReady, thread: "+android.os.Process.myTid()+Thread.currentThread().getName());
            return false;
          }
        })
        .into(img);
        */
  }

  public static class FunPicViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.content) public TextView content;
    @BindView(R.id.img) public ImageView img;

    public FunPicViewHolder(View view) {
      super(view);

      ButterKnife.bind(this,view);
    }
  }
}
