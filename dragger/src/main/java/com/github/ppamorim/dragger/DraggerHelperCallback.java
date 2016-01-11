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

import android.view.View;

/**
 * This class provides a helper to touch
 * and drag on DraggerView class.
 *
 * @author Pedro Paulo de Amorim
 *
 */
public class DraggerHelperCallback extends ViewDragHelper.Callback {

  private int dragState = 0;
  private int dragOffset = 0;

  private DraggerView draggerView;

  private DraggerHelperListener draggerListener;

  /**
   * The constructor get the instance of DraggableView
   *
   * @param draggerView provide the instance of DraggerView
   * @param dragView provide the instance inner view, this is inflated on DraggerView
   * @param draggerListener provide the instance of DraggerHelperListener class
   */
  public DraggerHelperCallback(DraggerView draggerView, View dragView,
      DraggerHelperListener draggerListener) {
    this.draggerView = draggerView;
    this.draggerListener = draggerListener;
  }

  /**
   * Check if view on focus is the DraggerView
   *
   * @param child return the view on focus
   * @param pointerId return the id of view
   * @return if the child on focus is equals the DraggerView
   */
  @Override public boolean tryCaptureView(View child, int pointerId) {
    return child.equals(draggerView.getDragView());
  }

  /**
   * Return the value of slide based
   * on left and width of the element
   *
   * @param child return the view on focus
   * @param left return the left size of DraggerView
   * @param dx return the scroll on x-axis
   * @return the offset of slide
   */
  @Override public int clampViewPositionHorizontal(View child, int left, int dx) {
    int leftBound = 0;
    int rightBound = 0;
    switch (draggerView.getDragPosition()) {
      case RIGHT:
        if (left > 0) {
          leftBound = draggerView.getPaddingLeft();
          rightBound = (int) draggerListener.dragHorizontalDragRange();
        }
        break;
      case LEFT:
        if (left < 0) {
          leftBound = (int) -draggerListener.dragHorizontalDragRange();
          rightBound = draggerView.getPaddingLeft();
        }
        break;
      default:
        break;
    }
    return Math.min(Math.max(left, leftBound), rightBound);
  }

  /**
   * Return the value of slide based
   * on top and height of the element
   *
   * @param child return the view on focus
   * @param top return the top size of DraggerView
   * @param dy return the scroll on y-axis
   * @return the offset of slide
   */
  @Override public int clampViewPositionVertical(View child, int top, int dy) {
    int topBound = 0;
    int bottomBound = 0;
    switch (draggerView.getDragPosition()) {
      case TOP:
        if (top > 0) {
          topBound = draggerView.getPaddingTop();
          bottomBound = (int) draggerListener.dragVerticalDragRange();
        }
        break;
      case BOTTOM:
        if (top < 0) {
          topBound = (int) -draggerListener.dragVerticalDragRange();
          bottomBound = draggerView.getPaddingTop();
        }
        break;
      default:
        break;
    }
    return Math.min(Math.max(top, topBound), bottomBound);
  }

  /**
   * Return the max value of view that can
   * slide based on #camplViewPositionHorizontal
   *
   * @param child return the view on focus
   * @return max horizontal distance that view on focus can slide
   */
  @Override public int getViewHorizontalDragRange(View child) {
    return (int) draggerListener.dragHorizontalDragRange();
  }

  /**
   * Return the max value of view that can
   * slide based on #clampViewPositionVertical
   *
   * @param child return the view on focus
   * @return max vertical distance that view on focus can slide
   */
  @Override public int getViewVerticalDragRange(View child) {
    return (int) draggerListener.dragVerticalDragRange();
  }

  /**
   * Verify if view is dragging or idle and
   * check dragOffset is bigger than dragRange,
   * if true, finish the activity.
   *
   * @param state return the touch state of view
   */
  @Override public void onViewDragStateChanged(int state) {
    if (state == dragState) {
      return;
    }
    if ((dragState == ViewDragHelper.STATE_DRAGGING
        || dragState == ViewDragHelper.STATE_SETTLING)
        && state == ViewDragHelper.STATE_IDLE
        && (dragOffset == draggerListener.dragHorizontalDragRange()
        || dragOffset == draggerListener.dragVerticalDragRange())) {
      draggerListener.finishActivity();
    }
    dragState = state;
  }

  /**
   * Override method used notify the drag value
   * based on position and dragRange
   *
   * @param left position.
   * @param top position.
   * @param dx change in X position from the last call.
   * @param dy change in Y position from the last call.
   */
  @Override public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
    super.onViewPositionChanged(changedView, left, top, dx, dy);
    float fractionScreen;
    switch (draggerView.getDragPosition()) {
      case TOP:
      case BOTTOM:
        dragOffset = Math.abs(top);
        fractionScreen = (float) dragOffset / draggerListener.dragVerticalDragRange();
        break;
      case LEFT:
      case RIGHT:
      default:
        dragOffset = Math.abs(left);
        fractionScreen = (float) dragOffset / draggerListener.dragHorizontalDragRange();
        break;
    }
    if (draggerListener != null) {
      draggerListener.onViewPositionChanged(fractionScreen >= 1 ? 1 : fractionScreen);
    }
  }

  /**
   * This is called only the touch on DraggerView is released.
   *
   * @param releasedChild return the view on focus
   * @param xVel return the speed of X animation
   * @param yVel return the speed of Y animation
   */
  @Override public void onViewReleased(View releasedChild, float xVel, float yVel) {
    super.onViewReleased(releasedChild, xVel, yVel);
    if (draggerView.isDragViewAboveTheLimit()) {
      switch (draggerView.getDragPosition()) {
        case LEFT:
          draggerView.closeFromCenterToLeft();
          break;
        case RIGHT:
          draggerView.closeFromCenterToRight();
          break;
        case TOP:
        default:
          draggerView.closeFromCenterToBottom();
          break;
        case BOTTOM:
          draggerView.closeFromCenterToTop();
          break;
      }
    } else {
      draggerView.moveToCenter();
    }
  }

}
