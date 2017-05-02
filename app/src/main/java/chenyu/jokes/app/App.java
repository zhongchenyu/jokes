package chenyu.jokes.app;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.text.Html;
import android.text.Spanned;
import chenyu.jokes.network.ServerAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by chenyu on 2017/3/3.
 */

public class App extends MultiDexApplication {

  private static ServerAPI serverAPI;
  private static Context context;

  @Override public void onCreate(){
    super.onCreate();

    context = getApplicationContext();
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(logging).build();

    serverAPI = new Retrofit.Builder()
        .baseUrl(ServerAPI.ENDPOINT)
        .addConverterFactory(JacksonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .client(httpClient)
        .build()
        .create(ServerAPI.class);
  }
  public static ServerAPI getServerAPI() {
    return serverAPI;
  }

  public static Context getAppContext() {
    return context;
  }
  @SuppressWarnings("deprecation")
  public static Spanned fromHtml(String html) {
    Spanned result;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
      result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
    } else {
      result = Html.fromHtml(html);
    }
    return result;
  }
}


