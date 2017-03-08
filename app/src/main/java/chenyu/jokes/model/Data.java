package chenyu.jokes.model;

import android.net.Uri;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by chenyu on 2017/3/7.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class Data {
  @JsonProperty("content") public String content;
  @JsonProperty("updatetime") public String time;
  @JsonProperty("url") public String url;

  public Uri getUri() {
    return Uri.parse(url);
  }
}
