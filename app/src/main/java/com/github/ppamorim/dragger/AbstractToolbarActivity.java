/*
* Copyright (C) 2015 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
