package chenyu.jokes.network;

import chenyu.jokes.model.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenyu on 2017/3/3.
 */

public interface ServerAPI {
  String ENDPOINT = "http://119.23.254.238/api/"; //http://119.23.13.228";

  @GET("jokes") Observable<Response> getJokes(
      @Query("page") int page
  );

  @GET("pictures") Observable<Response> getFunPic(
      @Query("page") int page
  );

  @GET("blacklist") Observable<Response> getBlackList();
}
