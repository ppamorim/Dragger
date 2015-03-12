package com.ppamorim.library.transformer;

import android.view.View;
import com.nineoldandroids.view.ViewHelper;

public class SlideTransformer extends Transformer {

  public SlideTransformer(View view, View parent) {
    super(view, parent);
  }

  @Override public void updatePosition(float verticalDragOffset) {
    ViewHelper.setPivotY(getView(), 100);
    ViewHelper.setAlpha(getView(), 0.5f);
  }

  @Override public boolean isViewAtBottom() {
    return getView().getBottom() == getParentView().getHeight();
  }

  @Override public int getMinHeightPlusMargin() {
    return getView().getHeight();
  }
}
