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

  private Bitmap bmp = null;
  private ImageView background;
  private FrameLayout dragView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    loadBackground();
  }

  @Override public void setContentView(int layoutResID) {
    super.setContentView(R.layout.dragger_sweet_panel);
    background = (ImageView) findViewById(R.id.image_background);
    dragView = (FrameLayout) findViewById(R.id.drag_view);
    dragView.addView(inflateLayout(layoutResID));
    background.setImageBitmap(bmp);
  }

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
