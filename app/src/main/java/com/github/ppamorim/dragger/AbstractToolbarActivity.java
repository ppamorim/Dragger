package com.github.ppamorim.dragger;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.InjectView;
import com.github.ppamorim.dragger.app.R;

public abstract class AbstractToolbarActivity extends AbstractActivity {

  private boolean enabledBackButton;

  @InjectView(R.id.toolbar) Toolbar toolbar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setToolbarTitle();
    setDisableBackButton(true);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        if (!enabledBackButton) {
          return false;
        }
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  public void setDisableBackButton(boolean isEnabled) {
    enabledBackButton = isEnabled;
    setSupportActionBar(getToolbar());
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
    }
  }

  public Toolbar getToolbar() {
    return toolbar;
  }

  public void setToolbarTitle() {
    toolbar.setTitle(getToolbarTitle());
  }

  protected abstract String getToolbarTitle();

}
