package chenyu.jokes.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by chenyu on 2017/5/16.
 */

public class JokeDBHelper extends SQLiteOpenHelper {

  private Context mContext;
private static final String CREATE_JOKE_COLLECTION = "create table joke_collections("
    + "id integer primary key autoincrement, "
    + "user_id integer,"
    + "joke_id integer, "
    + "content text)";


  public JokeDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context,name,factory,version);
    mContext = context;
  }
  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREATE_JOKE_COLLECTION);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
}
