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
package com.github.dragger;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.dragger.app.R;
import com.github.library.DraggerPosition;
import com.github.library.DraggerView;

public class BaseActivity extends ActionBarActivity {

  private Resources mResources;

  @InjectView(R.id.toolbar) Toolbar toolbar;
  @InjectView(R.id.dragger_view) DraggerView draggerView;

  @OnClick(R.id.menu) void onMenuClick() {
    startActivity(new Intent(this, BaseActivity.class));
  }

  @OnClick(R.id.left) void onLeftClick() {
    startDraggerActivity(DraggerPosition.LEFT);
  }

  @OnClick(R.id.right) void onRightClick() {
    startDraggerActivity(DraggerPosition.RIGHT);
  }

  @OnClick(R.id.top) void onTopClick() {
    startDraggerActivity(DraggerPosition.TOP);
  }

  @OnClick(R.id.bottom) void onBottomClick() {
    startDraggerActivity(DraggerPosition.BOTTOM);
  }

  @OnClick(R.id.panel) void onPanelClick() {
    startActivity(new Intent(this, PanelActivity.class));
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    ButterKnife.inject(this);
    configResources();
    configToolbar();
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
    draggerView.closeFromCenterToBottom();
  }

  private void configResources() {
    mResources = getResources();
  }

  private void configToolbar() {
    setSupportActionBar(toolbar);
    toolbar.setTitle(mResources.getString(R.string.app_name));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void startDraggerActivity(DraggerPosition dragPosition) {
    Intent intent = new Intent(this, DraggerActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    intent.putExtra(DraggerActivity.DRAG_POSITION, dragPosition);
    startActivity(intent);
  }

}
