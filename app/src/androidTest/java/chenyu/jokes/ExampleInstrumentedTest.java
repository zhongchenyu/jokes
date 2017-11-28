package chenyu.jokes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import android.util.Log;
import chenyu.jokes.util.Base64Util;
import chenyu.jokes.util.RSAUtil;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  @Test
  public void useAppContext() throws Exception {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("chenyu.jokes", appContext.getPackageName());
  }
  @Test
  public void encode() throws Exception {
    Context appContext = InstrumentationRegistry.getTargetContext();
    String str = RSAUtil.base64Encrypted("abcdef");
    //System.out.println(str);
    //Log.d("debug",str);
    throw new Exception(str);
  }

  @Test
  public void base64() throws  Exception {
    String str = Base64Util.encode();
    throw new Exception(str);
  }
}
