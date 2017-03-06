//TODO 测试代码，待删除
package chenyu.jokes.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import chenyu.jokes.R;
//import org.parceler.Parcels;



public class TestTabSwitchFragment extends Fragment {
  @BindView(R.id.txt_page_content) TextView mPageContent;


  //private final static String ARGUMENT_TEXT = "text";
  //private String mText = "test";

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    //View view = super.onCreateView(inflater, container, savedInstanceState);
    View view = inflater.inflate(R.layout.fragment_test_tab_switch, container, false);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    //mText = Parcels.unwrap(getArguments().getParcelable(ARGUMENT_TEXT));
    //mPageContent.setText(mText);
  }

  public static TestTabSwitchFragment newInstance() {
    TestTabSwitchFragment fragment = new TestTabSwitchFragment();
    //Bundle args = new Bundle();
    //args.putParcelable(ARGUMENT_TEXT, Parcels.wrap(text));
    //fragment.setArguments(args);
    return fragment;
  }



}
