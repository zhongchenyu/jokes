package chenyu.jokes.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.parceler.Parcel;

/**
 * Created by chenyu on 2017/3/3.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class Response {

  @JsonProperty("result") public Result result;

  //public static class Data {
  //  @SerializedName("data") public List<Joke> jokes;
  //}
}
