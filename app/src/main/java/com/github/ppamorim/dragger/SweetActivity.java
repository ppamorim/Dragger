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
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.ppamorim.dragger.app.R;

public class SweetActivity extends DraggerSweetActivity {

  @InjectView(R.id.container) LinearLayout container;
  @InjectView(R.id.hide_image) ImageView hideImage;

  @OnClick(R.id.sweet_parent) void onSweetParent() {
    startActivity(new Intent(this, SweetParentActivity.class));
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        hideImage.setVisibility(View.VISIBLE);
      }
    }, 1000);
  }

  @Override protected View getRootView() {
    return container;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sweet);
    ButterKnife.inject(this);
  }

}
