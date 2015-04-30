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

import android.content.Intent;
import butterknife.OnClick;
import com.github.ppamorim.dragger.app.R;

public class BaseActivity extends AbstractToolbarActivity {

  @OnClick(R.id.sweet) void onSweetClick() {
    startActivity(new Intent(this, SweetActivity.class));
  }

  @OnClick(R.id.edittext) void onEditTextClick() {
    startActivity(new Intent(this, EditTextActivity.class));
  }

  @OnClick(R.id.list) void onListClick() {
    startActivity(new Intent(this, ListActivity.class));
  }

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

  @OnClick(R.id.activity) void onActivityClick() {
    startActivity(new Intent(this, DraggingActivity.class));
  }

  @Override protected String getToolbarTitle() {
    return getResources().getString(R.string.app_name);
  }

  @Override protected int getContentViewId() {
    return R.layout.activity_base;
  }

  private void startDraggerActivity(DraggerPosition dragPosition) {
    Intent intent = new Intent(this, ImageActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    intent.putExtra(ImageActivity.DRAG_POSITION, dragPosition);
    startActivity(intent);
  }

}
