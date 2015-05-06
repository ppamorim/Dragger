package com.github.ppamorim.dragger;

import android.view.MenuItem;
import butterknife.InjectView;
import com.github.ppamorim.dragger.app.R;

public class EditTextActivity extends AbstractToolbarActivity {

  @InjectView(R.id.dragger_view) DraggerView draggerView;

  @Override protected String getToolbarTitle() {
    return getResources().getString(R.string.open_edittext);
  }

  @Override protected int getContentViewId() {
    return R.layout.activity_edittext;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onBackPressed() {
    draggerView.closeActivity();
  }


}
