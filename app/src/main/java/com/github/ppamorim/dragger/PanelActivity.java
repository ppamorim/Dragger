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
import android.view.LayoutInflater;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.ppamorim.dragger.app.R;

public class PanelActivity extends AbstractActivity {

  private LayoutInflater layoutInflater;

  @InjectView(R.id.dragger_panel) DraggerPanel draggerPanel;

  @Override protected int getContentViewId() {
    return R.layout.activity_panel;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    configLayoutInflater();
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    draggerPanel.initializeView();
    draggerPanel.addViewOnDrag(layoutInflater.inflate(R.layout.layout_content, draggerPanel, false));
    draggerPanel.addViewOnShadow(
        layoutInflater.inflate(R.layout.layout_shadow, draggerPanel, false));
  }

  private void configLayoutInflater() {
    layoutInflater = LayoutInflater.from(this);
  }

}