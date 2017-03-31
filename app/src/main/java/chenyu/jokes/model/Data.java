package chenyu.jokes.model;

import android.net.Uri;
import android.text.Spanned;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.parceler.Parcel;

import static chenyu.jokes.app.App.fromHtml;

/**
 * Created by chenyu on 2017/3/7.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class Data {
  public String content;
  @JsonProperty("updatetime") public String time;
  public String hashId;
  public String url;

  public Uri getUri() {
    return Uri.parse(url);
  }

  public Spanned getContent() {
    return fromHtml(content);
  }

}
