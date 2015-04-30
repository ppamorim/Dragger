package com.github.ppamorim.dragger;

import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;

public class ImageUtil {

  public static Bitmap captureScreenShot(Window window) {
    View v = window.getDecorView().getRootView();
    v.setDrawingCacheEnabled(true);
    Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
    v.setDrawingCacheEnabled(false);
    return bmp;
  }

}
