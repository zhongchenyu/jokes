package chenyu.jokes.model;

import android.net.Uri;
import android.text.Spanned;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.parceler.Parcel;

import static chenyu.jokes.app.App.fromHtml;

/**
 * Created by chenyu on 2017/3/7.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class Data {
  public int id;
  public String content;
  @JsonProperty("updatetime") public String time;
  public String hashId;
  public String url;
  public int up_amount;
  public int down_amount;
  public int collect_amount;
  public int comment_amount;
  public int my_attitude;
  public boolean my_collected;
  public Uri getUri() {
    return Uri.parse(url);
  }

  public Spanned getContent() {
    return fromHtml(content);
  }

  public String getTime() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日 H:mm");
    Date date = new Date(Long.valueOf(time)*1000);
    return  simpleDateFormat.format(date);
  }
}
