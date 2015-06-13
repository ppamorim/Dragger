package com.github.ppamorim.dragger;

import android.content.Context;
import android.util.AttributeSet;

public class LazyDraggerView extends DraggerView {

  public LazyDraggerView(Context context) {
    super(context);
    config();
  }

  public LazyDraggerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    config();
  }

  public LazyDraggerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    config();
  }

  public void config() {
    setRunAnimationOnFinishInflate(false);
  }

}
