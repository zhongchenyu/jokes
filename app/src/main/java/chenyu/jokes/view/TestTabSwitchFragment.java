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

public class TestTabSwitchFragment extends Fragment {
  @BindView(R.id.txt_page_content) TextView mPageContent;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_test_tab_switch, container, false);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  public static TestTabSwitchFragment newInstance() {
    TestTabSwitchFragment fragment = new TestTabSwitchFragment();
    return fragment;
  }



}
