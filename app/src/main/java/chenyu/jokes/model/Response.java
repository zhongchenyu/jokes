package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.parceler.Parcel;

/**
 * Created by chenyu on 2017/3/3.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class Response {
  public Result result;
  @JsonProperty("error_code") public int errorCode;
  public String reason;
}
