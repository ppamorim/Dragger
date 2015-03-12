package com.ppamorim.library;

import android.support.v4.widget.ViewDragHelper;
import android.view.View;

public class DragHelperCallback extends ViewDragHelper.Callback {

  private static final float X_MIN_VELOCITY = 1500;
  private static final float Y_MIN_VELOCITY = 1000;

  private DraggerView draggerView;
  private View dragView;
  private View shadowView;

  private DraggerListener draggerListener;

  public DragHelperCallback(DraggerView draggerView, View dragView, View shadowView, DraggerListener draggerListener) {
    this.draggerView = draggerView;
    this.dragView = dragView;
    this.shadowView = shadowView;
    this.draggerListener = draggerListener;
  }

  @Override public boolean tryCaptureView(View child, int pointerId) {
    return child.equals(dragView);
  }

  @Override
  public int clampViewPositionHorizontal(View child, int left, int dx) {
    final int leftBound = draggerView.getPaddingLeft();
    final int rightBound = draggerView.getWidth() - dragView.getWidth();
    final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
    return newLeft;
  }

  @Override
  public int clampViewPositionVertical(View child, int top, int dy) {
    final int topBound = draggerView.getPaddingTop();
    final int bottomBound = draggerView.getHeight() - dragView.getHeight();
    final int newTop = Math.min(Math.max(top, topBound), bottomBound);
    return newTop;
  }

  @Override public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
    super.onViewPositionChanged(changedView, left, top, dx, dy);
    int dragOffset = 0;
    //switch (dragEdge) {
    //  case TOP:
    //  case BOTTOM:
    //    // if (verticalDraging)
    //    dragOffset = Math.abs(top);
    //    break;
    //  case LEFT:
    //  case RIGHT:
    //    // if (horizontalDraging)
    //    dragOffset = Math.abs(left);
    //    break;
    //  default:
    //    break;
    //}

    dragOffset = Math.abs(top);

    float fractionScreen = (float) dragOffset / draggerListener.dragRange();
    if (fractionScreen >= 1) {
      fractionScreen = 1;
    }
    if (draggerListener != null) {
      draggerListener.onViewPositionChanged(fractionScreen);
    }
  }

  @Override public void onViewReleased(View releasedChild, float xVel, float yVel) {
    super.onViewReleased(releasedChild, xVel, yVel);
    triggerOnReleaseActionsWhileVerticalDrag(yVel);
  }

  private void triggerOnReleaseActionsWhileVerticalDrag(float yVel) {
    if (yVel < 0 && yVel <= -Y_MIN_VELOCITY) {
      draggerView.expand();
    } else if (yVel > 0 && yVel >= Y_MIN_VELOCITY) {
      draggerView.collapse();
    } else {
      if (draggerView.isDragViewAboveTheMiddle()) {
        draggerView.expand();
      } else {
        draggerView.collapse();
      }
    }
  }

}
