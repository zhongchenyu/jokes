package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by chenyu on 2017/5/1.
 */

@JsonIgnoreProperties(ignoreUnknown = true) public class Account {
  public User user;
  public String token;
}
