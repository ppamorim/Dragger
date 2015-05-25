package com.github.ppamorim.dragger;

import android.os.Bundle;
import android.os.Handler;
import butterknife.ButterKnife;
import com.github.ppamorim.dragger.app.R;

public class LazyActivity extends LazyDraggerActivity {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
    ButterKnife.inject(this);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        expand();
      }
    }, 1000);
  }
}
