package chenyu.jokes.feature.comment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.OnClick;
import chenyu.jokes.R;
import chenyu.jokes.base.BaseScrollActivity;
import chenyu.jokes.model.Comment;
import chenyu.jokes.model.Data;
import chenyu.jokes.presenter.CommentPresenter;
import nucleus.factory.RequiresPresenter;
import org.parceler.Parcels;

@RequiresPresenter(CommentPresenter.class)
public class JokeCommentActivity extends BaseScrollActivity<CommentAdapter, CommentPresenter> {
  private final static String ARGUMENT_JOKE = "argument_joke";
  private Data joke;
  @BindView(R.id.content) TextView jokeContent;
  @BindView(R.id.send) ImageButton send;
  @BindView(R.id.inputComment) EditText inputComment;
  private InputMethodManager inputMethodManager;

  public static void startActivity(Context context, Data joke) {
    Intent intent = new Intent(context, JokeCommentActivity.class);
    intent.putExtra(ARGUMENT_JOKE, Parcels.wrap(joke));
    context.startActivity(intent);
  }

  @Override public int getLayout() {
    return R.layout.activity_joke_comment;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setAdapter(new CommentAdapter());
    super.onCreate(savedInstanceState);

    joke = Parcels.unwrap(getIntent().getParcelableExtra(ARGUMENT_JOKE));
    jokeContent.setText(joke.getContent());

    getPresenter().getComment(getJokeId(), 1);

    inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
  }

  @Override protected void onResume() {
    super.onResume();
  }

  @OnClick(R.id.send) public void onClick(View view) {
    String comment = inputComment.getText().toString().trim();
    if (TextUtils.isEmpty(comment)) {
      Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
      return;
    }
    getPresenter().sendComment(comment);
  }

  public void onSendSuccess(Comment comment) {
    mAdapter.add(comment);
    mAdapter.notifyDataSetChanged();
    inputComment.setText("");
    Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show();
    inputMethodManager.hideSoftInputFromWindow(inputComment.getWindowToken(), 0);
    recyclerView.smoothScrollToPosition(mAdapter.getItemCount());
  }

  public int getJokeId() {
    return joke.id;
  }
}
