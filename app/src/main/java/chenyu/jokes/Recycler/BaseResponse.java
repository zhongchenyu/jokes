package chenyu.jokes.model;

//import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by chenyu on 2017/3/7.
 */

public class BaseResponse<T> {
 // @SerializedName("result") public Data data;

  public static class Data<T> {
 //   @SerializedName("data") public List<T> items;
  }
}
