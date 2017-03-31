package chenyu.jokes.network;

import chenyu.jokes.model.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by chenyu on 2017/3/3.
 */

public interface ServerAPI {
  String ENDPOINT = "http://119.23.13.228";

  @GET("/content.php") Observable<Response> getJokes(
      @Query("page") int page
  );

  @GET("/img.php") Observable<Response> getFunPic(
      @Query("page") int page
  );

  @GET("/blacklist.json") Observable<Response> getBlackList();
}
