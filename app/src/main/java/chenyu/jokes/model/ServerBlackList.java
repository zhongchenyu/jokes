package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import org.parceler.Parcel;

/**
 * Created by chenyu on 2017/3/9.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class ServerBlackList {
  @JsonProperty("hashId") public String hashId;

}
