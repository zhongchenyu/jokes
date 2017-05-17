package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.parceler.Parcel;

/**
 * Created by chenyu on 2017/5/1.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class User {
  public String id;
  public String name;
  public String email;
}
