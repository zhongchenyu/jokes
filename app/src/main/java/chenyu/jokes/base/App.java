package chenyu.jokes.base;

import android.app.Application;
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
}


