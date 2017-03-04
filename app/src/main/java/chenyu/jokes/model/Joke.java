package chenyu.jokes.model;

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
}
