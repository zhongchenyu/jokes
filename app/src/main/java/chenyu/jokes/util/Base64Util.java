package chenyu.jokes.util;

import android.util.Base64;

/**
 * Created by chenyu on 2017/11/24.
 */

public class Base64Util {

  public static String encode() {
    String str = "abcdef";

    String result = Base64.encodeToString(str.getBytes(), Base64.NO_WRAP);
    System.out.print(result);
    return result;
  }
}
