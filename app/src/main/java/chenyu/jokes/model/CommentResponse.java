package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import org.parceler.Parcel;
import org.parceler.ParcelClass;

/**
 * Created by chenyu on 2017/5/15.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class CommentResponse {
  public ArrayList<Comment> data;
}
