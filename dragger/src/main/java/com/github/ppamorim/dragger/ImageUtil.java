package com.github.ppamorim.dragger;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.Window;

public class ImageUtil {

  public static Bitmap captureScreenShot(View view) {
    Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
        view.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    view.draw(canvas);
    return bitmap;
  }

}
