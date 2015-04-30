package com.github.ppamorim.dragger;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class DraggerSweetActivity extends DraggerActivity{

  public static final String BACKGROUND = "background";

  @Override public void startActivity(Intent intent) {
    try {
      Bitmap bitmap = ImageUtil.captureScreenShot(getRootView());
      String filename = "bitmap.png";
      FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
      stream.close();
      bitmap.recycle();
      intent.putExtra(BACKGROUND, filename);
      super.startActivity(intent);
    } catch (FileNotFoundException filee) {
      filee.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  protected abstract View getRootView();

}

