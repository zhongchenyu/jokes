package chenyu.jokes.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by chenyu on 2017/3/3.
 */

public class Response {
  @SerializedName("result") public Data data;

  public static class Data {
    @SerializedName("data") public Item[] items;

  }
}
