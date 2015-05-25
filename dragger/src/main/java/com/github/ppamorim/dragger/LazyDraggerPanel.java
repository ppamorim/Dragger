package com.github.ppamorim.dragger;

import android.content.Context;
import android.util.AttributeSet;

public class LazyDraggerPanel extends BaseDraggerPanel {

  private LazyDraggerView lazyDraggerView;

  public LazyDraggerPanel(Context context) {
    super(context);
  }

  public LazyDraggerPanel(Context context, AttributeSet attrs) {
    super(context, attrs);
    initializeAttributes(attrs);
  }

  public LazyDraggerPanel(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initializeAttributes(attrs);
  }

  public LazyDraggerView getLazyDraggerView() {
    return lazyDraggerView;
  }

  public void setLazyDraggerLimit(float draggerLimit) {
    lazyDraggerView.setDraggerLimit(draggerLimit);
  }

  public void setLazyDraggerPosition(DraggerPosition dragPosition) {
    lazyDraggerView.setDraggerPosition(dragPosition);
  }

  public void setLazyDraggerCallback(DraggerCallback draggerCallback) {
    lazyDraggerView.setDraggerCallback(draggerCallback);
  }

  public void setSlideEnabled(boolean enabled) {
    lazyDraggerView.setSlideEnabled(enabled);
  }

  public void initializeView() {
    super.initializeView(R.layout.lazy_dragger_panel);
    lazyDraggerView = (LazyDraggerView) findViewById(R.id.dragger_view);
    if (attributes != null) {
      setLazyDraggerLimit(draggerLimit);
      setLazyDraggerPosition(DraggerPosition.getDragPosition(draggerPosition));
    }
  }

  public void closeActivity() {
    lazyDraggerView.closeActivity();
  }

  public void expand() {
    lazyDraggerView.expand();
  }

}
