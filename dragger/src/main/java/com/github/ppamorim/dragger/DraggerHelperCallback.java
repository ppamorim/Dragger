/*
* Copyright (C) 2015 Pedro Paulo de Amorim
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.github.ppamorim.dragger;

import android.support.v4.widget.ViewDragHelper;
import android.view.View;

public class DraggerHelperCallback extends ViewDragHelper.Callback {

  private int dragState = 0;
  private int dragOffset = 0;

  private DraggerView draggerView;
  private View dragView;

  private DraggerPosition dragPosition;
  private DraggerHelperConfig draggerHelperConfig;
  private DraggerHelperListener draggerListener;

  public DraggerHelperCallback(DraggerView draggerView, View dragView, DraggerPosition dragPosition,
      DraggerHelperConfig draggerHelperConfig) {
    this.draggerView = draggerView;
    this.dragView = dragView;
    this.dragPosition = DraggerPosition.values()[dragPosition.getPosition()];
    this.draggerHelperConfig = draggerHelperConfig;
  }

  public void setDraggerListener(DraggerHelperListener draggerListener) {
    if(draggerListener != null) {
      this.draggerListener = draggerListener;
    }
  }

  public DraggerPosition getDragPosition() {
    return dragPosition;
  }

  public void setDragPosition(DraggerPosition dragPosition) {
    this.dragPosition = dragPosition;
  }

  @Override public boolean tryCaptureView(View child, int pointerId) {
    return child.equals(dragView);
  }

  @Override public int clampViewPositionHorizontal(View child, int left, int dx) {
    int leftBound = 0;
    int rightBound = 0;
    switch (dragPosition) {
      case RIGHT:
        if (left > 0) {
          leftBound = draggerView.getPaddingLeft();
          rightBound = (int) draggerHelperConfig.dragHorizontalDragRange();
        }
        break;
      case LEFT:
        if (left < 0) {
          leftBound = (int) -draggerHelperConfig.dragHorizontalDragRange();
          rightBound = draggerView.getPaddingLeft();
        }
        break;
      default:
        break;
    }
    return Math.min(Math.max(left, leftBound), rightBound);
  }

  @Override public int clampViewPositionVertical(View child, int top, int dy) {
    int topBound = 0;
    int bottomBound = 0;
    switch (dragPosition) {
      case TOP:
        if (top > 0) {
          topBound = draggerView.getPaddingTop();
          bottomBound = (int) draggerHelperConfig.dragVerticalDragRange();
        }
        break;
      case BOTTOM:
        if (top < 0) {
          topBound = (int) -draggerHelperConfig.dragVerticalDragRange();
          bottomBound = draggerView.getPaddingTop();
        }
        break;
      default:
        break;
    }
    return Math.min(Math.max(top, topBound), bottomBound);
  }

  @Override public int getViewVerticalDragRange(View child) {
    return (int) draggerHelperConfig.dragVerticalDragRange();
  }

  @Override public int getViewHorizontalDragRange(View child) {
    return (int) draggerHelperConfig.dragHorizontalDragRange();
  }

  @Override public void onViewDragStateChanged(int state) {
    if (state == dragState || draggerListener == null) {
      return;
    }
    if ((dragState == ViewDragHelper.STATE_DRAGGING
        || dragState == ViewDragHelper.STATE_SETTLING)
        && state == ViewDragHelper.STATE_IDLE) {
      switch (dragPosition) {
        case LEFT:
        case RIGHT:
          if (dragOffset == draggerHelperConfig.dragHorizontalDragRange()) {
            draggerListener.finishActivity();
          }
          break;
        default:
        case TOP:
        case BOTTOM:
          if (dragOffset == draggerHelperConfig.dragVerticalDragRange()) {
            draggerListener.finishActivity();
          }
          break;
      }
    }
    dragState = state;
  }

  @Override public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
    super.onViewPositionChanged(changedView, left, top, dx, dy);
    float fractionScreen;
    switch (dragPosition) {
      case TOP:
      case BOTTOM:
        dragOffset = Math.abs(top);
        fractionScreen = (float) dragOffset / draggerHelperConfig.dragVerticalDragRange();
        break;
      case LEFT:
      case RIGHT:
      default:
        dragOffset = Math.abs(left);
        fractionScreen = (float) dragOffset / draggerHelperConfig.dragHorizontalDragRange();
        break;
    }

    System.out.println("fractionScreen>: " + fractionScreen);

    if (fractionScreen >= 1) {
      fractionScreen = 1;
    }

    if (draggerListener != null) {
      draggerListener.onViewPositionChanged(fractionScreen);
    } else {
      System.out.println("draggerListener null ");
    }
  }

  @Override public void onViewReleased(View releasedChild, float xVel, float yVel) {
    super.onViewReleased(releasedChild, xVel, yVel);
    switch (dragPosition) {
      case LEFT:
        if (draggerView.isDragViewAboveTheMiddle()) {
          draggerView.closeFromCenterToLeft();
        } else {
          draggerView.moveToCenter();
        }
        break;
      case RIGHT:
        if (draggerView.isDragViewAboveTheMiddle()) {
          draggerView.closeFromCenterToRight();
        } else {
          draggerView.moveToCenter();
        }
        break;
      case TOP:
      default:
        if (draggerView.isDragViewAboveTheMiddle()) {
          draggerView.closeFromCenterToBottom();
        } else {
          draggerView.moveToCenter();
        }
        break;
      case BOTTOM:
        if (draggerView.isDragViewAboveTheMiddle()) {
          draggerView.closeFromCenterToTop();
        } else {
          draggerView.moveToCenter();
        }
        break;
    }
  }

}
