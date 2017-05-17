package chenyu.jokes.presenter;

import android.os.Bundle;
import chenyu.jokes.app.App;
import chenyu.jokes.base.BaseScrollPresenter;
import chenyu.jokes.feature.comment.JokeCommentActivity;
import chenyu.jokes.model.CommentResponse;
import chenyu.jokes.model.Comment;
import chenyu.jokes.model.SendCommentResponse;
import java.util.ArrayList;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;

import static rx.android.schedulers.AndroidSchedulers.mainThread;
import static rx.schedulers.Schedulers.io;

/**
 * Created by chenyu on 2017/5/15.
 */

public class CommentPresenter extends BaseScrollPresenter<JokeCommentActivity> {
  private int mPage;
  private int mJokeId;
  private String mComment;
  private final int GET_COMMENT = 1;
  private final int SEND_COMMENT = 2;

  @Override protected void onCreate(Bundle savedState) {
    super.onCreate(savedState);

    restartableFirst(GET_COMMENT,
        new Func0<Observable<CommentResponse>>() {
          @Override public Observable<CommentResponse> call() {
            return App.getServerAPI().getComment(getSendToken(), mJokeId,mPage)
                .subscribeOn(io())
                .observeOn(mainThread());
          }
        },
        new Action2<JokeCommentActivity, CommentResponse>() {
          @Override public void call(JokeCommentActivity jokeCommentActivity,
              CommentResponse arrayListBaseResponse) {
            jokeCommentActivity.onItemsNext(arrayListBaseResponse.data);
          }
        },
        new Action2<JokeCommentActivity, Throwable>() {
          @Override public void call(JokeCommentActivity jokeCommentActivity, Throwable throwable) {
            jokeCommentActivity.onItemsError(throwable);
          }
        }
    );
    restartableFirst(SEND_COMMENT,
        new Func0<Observable<SendCommentResponse>>() {
          @Override public Observable<SendCommentResponse> call() {
            return App.getServerAPI().sendComment(getSendToken(), mJokeId, mComment)
                .subscribeOn(io()).observeOn(mainThread());
          }
        },
        new Action2<JokeCommentActivity, SendCommentResponse>() {
          @Override public void call(JokeCommentActivity jokeCommentActivity,
              SendCommentResponse sendCommentResponse) {

            jokeCommentActivity.onSendSuccess(sendCommentResponse.data);
          }
        },
        new Action2<JokeCommentActivity, Throwable>() {
          @Override public void call(JokeCommentActivity jokeCommentActivity, Throwable throwable) {
            jokeCommentActivity.onItemsError(throwable);
          }
        }
    );

  }

  @Override public void request(int page) {
    mPage = page;
    start(GET_COMMENT);
  }

  public void getComment(int jokeId, int page) {
    mPage = page;
    mJokeId = jokeId;
    start(GET_COMMENT);
  }

  public void setJokeId(int jokeId) {
    mJokeId = jokeId;
  }
  public void sendComment(String comment) {
    mComment = comment;
    start(SEND_COMMENT);
  }
}
