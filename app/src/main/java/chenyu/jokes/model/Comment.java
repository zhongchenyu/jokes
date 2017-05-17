package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.parceler.Parcel;

/**
 * Created by chenyu on 2017/5/15.
 */

@Parcel @JsonIgnoreProperties(ignoreUnknown = true) public class Comment {
  public int id;
  public User user;
  public String comment;
  public Long time;
  public Comment reply;

  public String getTime() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日 H:mm");
    Date date = new Date(Long.valueOf(time)*1000);
    return  simpleDateFormat.format(date);
  }
}
