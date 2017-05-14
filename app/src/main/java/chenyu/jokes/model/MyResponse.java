package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import org.parceler.Parcel;

/**
 * Created by chenyu on 2017/5/13.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class MyResponse {
  public ArrayList<Data> data;
  public String message;
}
