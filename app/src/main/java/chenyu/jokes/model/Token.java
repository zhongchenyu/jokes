package chenyu.jokes.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by chenyu on 2017/5/1.
 */

@JsonIgnoreProperties(ignoreUnknown = true) public class Token {
  public String token;
}
