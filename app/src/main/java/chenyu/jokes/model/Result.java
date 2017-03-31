package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import org.parceler.Parcel;

/**
 * Created by chenyu on 2017/3/7.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class Result {
  public ArrayList<Data> data;
}
