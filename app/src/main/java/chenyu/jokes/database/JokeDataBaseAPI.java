package chenyu.jokes.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import chenyu.jokes.app.AccountManager;
import chenyu.jokes.app.App;
import chenyu.jokes.model.Data;
import chenyu.jokes.model.JokeCollection;
import java.util.ArrayList;
import rx.Observable;

/**
 * Created by chenyu on 2017/5/16.
 */

public class JokeDataBaseAPI {
  private Context mContext;
  private JokeDBHelper dbHelper;
  private SQLiteDatabase db ;
  private ContentValues values;

  public JokeDataBaseAPI() {
    mContext = App.getAppContext();
    dbHelper = new JokeDBHelper(mContext,"Joke.db", null, 1);
    db = dbHelper.getWritableDatabase();
    values = new ContentValues();
  }

  public static JokeDataBaseAPI create() {
    return new JokeDataBaseAPI();
  }

  public void insertJoke(Data joke) {
    values.put("user_id", Integer.parseInt(AccountManager.create().getAccount().user.id));
    values.put("joke_id", joke.id);
    values.put("content", joke.content);
    db.insert("joke_collections", null, values);
    values.clear();
  }

  public void deleteJokeById(int collectionId) {
    db.delete("joke_collections", "id = ?", new String[]{String.valueOf(collectionId)});
  }

  public void deleteJokeByJokeId(int jokeId) {
    String userId = AccountManager.create().getAccount().user.id;
    db.delete("joke_collections", "user_id = ? and joke_id = ?", new String[]{userId,String.valueOf(jokeId)});
  }

  public Observable<ArrayList<JokeCollection>> getJoke() {
    ArrayList<JokeCollection> list = new ArrayList<>();

    Cursor cursor = db.query("joke_collections",
        new String[] {"id,user_id,joke_id,content"},
        "user_id = ?",
        new String[]{AccountManager.create().getAccount().user.id},
        null,null,null);

    if(cursor.moveToFirst()) {
      do {
        JokeCollection jokeCollection = new JokeCollection();
        jokeCollection.id = cursor.getInt(cursor.getColumnIndex("id"));
        jokeCollection.userId = cursor.getInt(cursor.getColumnIndex("user_id"));
        jokeCollection.jokeId = cursor.getInt(cursor.getColumnIndex("joke_id"));
        jokeCollection.content = cursor.getString(cursor.getColumnIndex("content"));
        list.add(jokeCollection);
      } while (cursor.moveToNext());

      cursor.close();
    }
    return Observable.just(list);
  }
}
