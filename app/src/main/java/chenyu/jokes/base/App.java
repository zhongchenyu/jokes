package chenyu.jokes.base;

import android.app.Application;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
//import retrofit2.RestAdapter;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
/**
 * Created by chenyu on 2017/3/3.
 */

public class App extends Application {
  private static App instance;
  private static ServerAPI serverAPI;

  @Override public void onCreate(){
    super.onCreate();
    instance = this;

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


