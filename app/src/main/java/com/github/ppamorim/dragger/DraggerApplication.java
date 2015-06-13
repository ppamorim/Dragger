package com.github.ppamorim.dragger;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;

public class DraggerApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    Fresco.initialize(getApplicationContext());
  }

  @Override public void onTerminate() {
    super.onTerminate();
    Fresco.shutDown();
  }

}
