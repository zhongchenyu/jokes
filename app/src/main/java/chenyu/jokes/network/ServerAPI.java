package chenyu.jokes.network;

import android.support.annotation.Nullable;
import chenyu.jokes.app.App;
import chenyu.jokes.model.Account;
import chenyu.jokes.model.MyResponse;
import chenyu.jokes.model.Notice;
import chenyu.jokes.model.Response;
import chenyu.jokes.model.Token;
import chenyu.jokes.model.User;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by chenyu on 2017/3/3.
 */

public interface ServerAPI {
  String ENDPOINT = "http://119.23.254.238/api/"; //http://119.23.13.228";

  @GET("my_jokes") Observable<MyResponse> getJokes(
      @Nullable @Header("Authorization") String token,
      @Query("page") int page
  );

  @GET("my_images") Observable<MyResponse> getFunPic(
      @Nullable  @Header("Authorization") String token,
      @Query("page") int page
  );

  @GET("blacklist") Observable<Response> getBlackList();

  @FormUrlEncoded @POST("register") Observable<Token> register(
      @Field("name") String name,
      @Field("email") String email,
      @Field("password") String password
  );

  @GET("login") Observable<Account> login(
      @Query("email") String email,
      @Query("password") String password
  );


  @GET("notices") Observable<Notice> getNotice(
      @Header("Authorization") String token
  );

  @GET("user") Observable<User> getUserInfo(
      @Header("Authorization") String token
  );

  @POST("my_jokes/{joke_id}/{action}") Observable<MyResponse> attitude(
      @Header("Authorization") String token,
      @Path("joke_id") int jokeId,
      @Path("action") String action
  );
}
