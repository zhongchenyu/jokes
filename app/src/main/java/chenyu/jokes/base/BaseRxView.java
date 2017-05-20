package chenyu.jokes.base;

import java.util.ArrayList;

/**
 * Created by chenyu on 2017/5/20.
 */

public interface BaseRxView<Model> {
  void onItemsNext(ArrayList<Model> model);

  void onItemsError(Throwable throwable);
}
