package chenyu.jokes.model;

import android.text.Html;
import android.text.Spanned;
import com.google.gson.annotations.SerializedName;

/**
 * Created by chenyu on 2017/3/3.
 */

public class Joke {
  @SerializedName("content") public String content;
  @SerializedName("updatetime") public String updatetime;
  @Override public String toString() {
    return content
        + "\n"
        + updatetime;
  }

  public String getContent() {
    return fromHtml(content).toString();
  }

  @SuppressWarnings("deprecation")
  public static Spanned fromHtml(String html) {
    Spanned result;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
    } else {
      result = Html.fromHtml(html);
    }
    return result;
  }
}
