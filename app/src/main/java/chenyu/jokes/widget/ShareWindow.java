package chenyu.jokes.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import chenyu.jokes.R;
import chenyu.jokes.app.App;
import chenyu.jokes.feature.main.MainActivity;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;

/**
 * Created by chenyu on 2017/5/11.
 */

public class ShareWindow extends PopupWindow {
  @BindView(R.id.cancel) public TextView cancelShare;
  @BindView(R.id.wechat) public ImageView wechat;
  @BindView(R.id.wechat_moment) public ImageView wechatMoment;
  @BindView(R.id.more) public ImageView more;

  private static Context mContext;
  private String sharedText;

  public ShareWindow(View contentView, int width, int height, boolean focusable) {
    super(contentView, width, height, focusable);

  }

  public static ShareWindow  create(Context context) {
    mContext = context;
    View shareView = LayoutInflater.from(context).inflate(R.layout.window_share, null);
    ShareWindow shareWindow = new ShareWindow(shareView, WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT, true);
    ButterKnife.bind(shareWindow, shareView);
    shareWindow.setAnimationStyle(R.style.AnimBottom);
    return shareWindow;
  }

  @OnClick({R.id.cancel, R.id.wechat, R.id.wechat_moment, R.id.more}) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.cancel:
        dismiss();
        break;
      case R.id.wechat_moment:
        shareTextToWechat(SendMessageToWX.Req.WXSceneTimeline);
        dismiss();
        break;
      case R.id.wechat:
        shareTextToWechat(SendMessageToWX.Req.WXSceneSession);
        dismiss();
        break;
      case R.id.more:
        systemShare();
        dismiss();
        break;
    }
  }
  public ShareWindow setText(String text) {
    sharedText = text;
    return this;
  }

  private void systemShare() {
    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
    sharingIntent.setType("text/plain");
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharedText);
    mContext.startActivity(Intent.createChooser(sharingIntent, mContext.getString(R.string.send_intent_title)));
  }
  private void shareTextToWechat(int scene) {
    WXTextObject textObj = new WXTextObject();
    textObj.text = sharedText;

    WXMediaMessage msg = new WXMediaMessage();
    msg.mediaObject = textObj;
    msg.description = sharedText;

    SendMessageToWX.Req req = new SendMessageToWX.Req();
    req.transaction = buildTransaction("text");
    req.message = msg;
    req.scene = scene;
    App.getWXAPI().sendReq(req);
  }

  private String buildTransaction(final String type) {
    return (type == null) ? String.valueOf(System.currentTimeMillis())
        : type + System.currentTimeMillis();
  }

}
