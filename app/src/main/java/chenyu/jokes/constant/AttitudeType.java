package chenyu.jokes.constant;

/**
 * Created by chenyu on 2017/5/14.
 */

public enum AttitudeType {
  UP("up", 1), DOWN("down", 2), COLLECT("collection", 3);

  private String path;
  private int index;

  private AttitudeType(String path, int index) {
    this.path = path;
    this.index = index;
  }

  public String getPath() {
    return path;
  }

  public int getIndex() {
    return index;
  }
}
