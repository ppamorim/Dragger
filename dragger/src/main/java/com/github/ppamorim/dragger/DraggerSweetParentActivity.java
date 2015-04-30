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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import java.io.FileInputStream;

public class DraggerSweetParentActivity extends AppCompatActivity {

  private Bitmap bmp;
  private ImageView background;
  private FrameLayout dragView;
  private DraggerView draggerView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    loadBackground();
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(R.layout.dragger_sweet_panel);
    draggerView = (DraggerView) findViewById(R.id.dragger_view);
    background = (ImageView) findViewById(R.id.image_background);
    dragView = (FrameLayout) findViewById(R.id.drag_view);
    dragView.addView(inflateLayout(layoutResID));
    background.setImageBitmap(bmp);
    draggerView.setDraggerHelperListener(onDraggerHelperListener);
  }

  private DraggerHelperListener onDraggerHelperListener = new DraggerHelperListener() {
    @Override public void finishActivity() {
      finish();
    }

    @Override public void onViewPositionChanged(float dragValue) {
      System.out.println("DRAG: " + dragValue);
      //ViewHelper.setY(background, (int) dragValue);
    }
  };

  private View inflateLayout(int layoutResID) {
    return LayoutInflater.from(this).inflate(layoutResID, null);
  }

  public void loadBackground() {
    try {
      FileInputStream is = this.openFileInput(getIntent()
          .getStringExtra(DraggerSweetActivity.BACKGROUND));
      bmp = BitmapFactory.decodeStream(is);
      is.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
