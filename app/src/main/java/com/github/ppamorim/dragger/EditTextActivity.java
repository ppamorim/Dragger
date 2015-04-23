package com.github.ppamorim.dragger;

import com.github.ppamorim.dragger.app.R;

public class EditTextActivity extends AbstractToolbarActivity {

  @Override protected String getToolbarTitle() {
    return getResources().getString(R.string.open_edittext);
  }

  @Override protected int getContentViewId() {
    return R.layout.activity_edittext;
  }
}
