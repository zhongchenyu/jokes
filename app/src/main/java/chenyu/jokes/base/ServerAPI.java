package chenyu.jokes.base;

import chenyu.jokes.model.FunPicResponse;
import chenyu.jokes.model.Response;
import chenyu.jokes.model.ServerBlackList;
import retrofit2.http.Query;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by chenyu on 2017/3/3.
 */

public interface ServerAPI {

public static final String ENDPOINT = "http://119.23.13.228";

  @GET("/content.php") Observable<Response> getJokes(
      //@Query("key") String api_key,
      //@Query("sort") String sort,
      @Query("page") int page
      //@Query("pagesize") int pagesize,
      //@Query("time") String time
  );


  @GET("/img.php") Observable<Response> getFunPic(
      //@Query("key") String api_key,
      //@Query("sort") String sort,
      @Query("page") int page
      //@Query("pagesize") int pagesize,
      //@Query("time") String time
  );

  @GET("/blacklist.json") Observable<Response> getBlackList();
}
