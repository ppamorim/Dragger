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

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.nineoldandroids.view.ViewHelper;

/**
 * Class created to extends a FrameLayout, that's a root of the view
 * It contains a dragView(the content) and a shadowView(for dim)
 * You must add this elements, otherwise
 *
 * @author Pedro Paulo de Amorim
 */
public class DraggerView extends FrameLayout {

  private static final int DELAY = 200;
  private static final float SLIDE_IN = 1f;

  private static final int MAX_ALPHA = 1;
  private static final int MIN_ALPHA = 0;

  private static final float SENSITIVITY = 1.0f;
  private static final float DEFAULT_DRAG_LIMIT = 0.5f;
  private static final int DEFAULT_DRAG_POSITION = DraggerPosition.TOP.getPosition();
  private static final int INVALID_POINTER = -1;

  private boolean canFinish = false;
  private boolean canSlide = true;
  private int activePointerId = INVALID_POINTER;
  private float verticalDragRange;
  private float horizontalDragRange;
  private float dragLimit;

  private TypedArray attributes;
  private DraggerPosition dragPosition;

  private DraggerCallback draggerCallback;
  private DraggerHelperCallback dragHelperCallback;
  private DraggerHelperListener draggerListener;
  private ViewDragHelper dragHelper;
  private View dragView;
  private View shadowView;

  private Handler handler = new Handler();

  public DraggerView(Context context) {
    super(context);
  }

  public DraggerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initializeAttributes(attrs);
  }

  public DraggerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initializeAttributes(attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (!isInEditMode()) {
      mapGUI(attributes);
      attributes.recycle();
      configDragViewHelper();
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int measureWidth = MeasureSpec.makeMeasureSpec(
        getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
        MeasureSpec.EXACTLY);
    int measureHeight = MeasureSpec.makeMeasureSpec(
        getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
        MeasureSpec.EXACTLY);
    if (dragView != null) {
      dragView.measure(measureWidth, measureHeight);
      setViewAlpha(dragView, MIN_ALPHA);
      closeActivity();
      expandWithDelay();
    }
  }

  @Override protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    setVerticalDragRange(height);
    setHorizontalDragRange(width);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (!isEnabled() || isListOnTop()) {
      return false;
    }
    final int action = MotionEventCompat.getActionMasked(ev);
    switch (action) {
      case MotionEvent.ACTION_CANCEL:
      case MotionEvent.ACTION_UP:
        dragHelper.cancel();
        return false;
      case MotionEvent.ACTION_DOWN:
        int index = MotionEventCompat.getActionIndex(ev);
        activePointerId = MotionEventCompat.getPointerId(ev, index);
        if (activePointerId == INVALID_POINTER) {
          return false;
        }
      default:
        return dragHelper.shouldInterceptTouchEvent(ev);
    }
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    int actionMasked = MotionEventCompat.getActionMasked(ev);
    if ((actionMasked & MotionEventCompat.ACTION_MASK) == MotionEvent.ACTION_DOWN) {
      activePointerId = MotionEventCompat.getPointerId(ev, actionMasked);
    }
    if (activePointerId == INVALID_POINTER) {
      return false;
    }
    dragHelper.processTouchEvent(ev);
    boolean isDragViewHit = isViewHit(dragView, (int) ev.getX(), (int) ev.getY());
    boolean isSecondViewHit = isViewHit(shadowView, (int) ev.getX(), (int) ev.getY());
    return isDragViewHit || isSecondViewHit;
  }

  @Override public void computeScroll() {
    if (!isInEditMode() && dragHelper.continueSettling(true)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  private float getVerticalDragRange() {
    return verticalDragRange;
  }

  private void setVerticalDragRange(float verticalDragRange) {
    this.verticalDragRange = verticalDragRange;
  }

  private float getHorizontalDragRange() {
    return horizontalDragRange;
  }

  private void setHorizontalDragRange(float horizontalDragRange) {
    this.horizontalDragRange = horizontalDragRange;
  }

  public void setListViewPosition(final ListView listView) {
    listView.setOnScrollListener(new AbsListView.OnScrollListener() {
      @Override public void onScrollStateChanged(AbsListView view, int scrollState) {

      }

      @Override public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
          int totalItemCount) {

        switch (dragPosition) {
          case LEFT:
            //parentSize = dragView.getWidth();
            //viewAxisPosition = -ViewHelper.getX(dragView) + (parentSize * dragLimit);
            break;
          case RIGHT:
            //parentSize = dragView.getWidth();
            //viewAxisPosition = ViewHelper.getX(dragView) + (parentSize * dragLimit);
            break;
          case TOP:
          default:
            if(firstVisibleItem > 0) {
              return;
            }
            setCanSlide(canScrollUp(listView));
            break;
          case BOTTOM:
            if(firstVisibleItem < totalItemCount) {
              return;
            }
            setCanSlide(canScrollDown(listView));
            break;
        }

      }
    });

  }

  public boolean isListOnTop() {
    return canSlide;
  }

  public void setCanSlide(boolean canSlide) {
    this.canSlide = canSlide;
  }

  public boolean canScrollUp(View view) {
    if (Build.VERSION.SDK_INT < 14) {
      if (view instanceof AbsListView) {
        final AbsListView absListView = (AbsListView) view;
        return absListView.getChildCount() > 0
            && (absListView.getFirstVisiblePosition() > 0 || absListView
            .getChildAt(0).getTop() < absListView.getPaddingTop());
      } else {
        return view.getScrollY() > 0;
      }
    } else {
      return ViewCompat.canScrollVertically(view, -1);
    }
  }

  public boolean canScrollDown(View view) {
    if (Build.VERSION.SDK_INT < 14) {
      if (view instanceof AbsListView) {
        final AbsListView absListView = (AbsListView) view;
        return absListView.getChildCount() > 0
            && (absListView.getFirstVisiblePosition() > 0 || absListView
            .getChildAt(0).getBottom() < absListView.getPaddingBottom());
      } else {
        return view.getScrollY() > 0;
      }
    } else {
      return ViewCompat.canScrollVertically(view, -1);
    }
  }

  public float getDragLimit() {
    return dragLimit;
  }

  public void setDraggerLimit(float dragLimit) {
    this.dragLimit = dragLimit;
  }

  public DraggerPosition getDragPosition() {
    return dragPosition;
  }

  public void setDraggerPosition(DraggerPosition dragPosition) {
    this.dragPosition = dragPosition;
    dragHelperCallback.setDragPosition(dragPosition);
  }

  private void initializeAttributes(AttributeSet attrs) {
    TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.dragger_layout);
    this.dragLimit = attributes.getFloat(R.styleable.dragger_layout_drag_limit,
        DEFAULT_DRAG_LIMIT);
    this.dragPosition = DraggerPosition.getDragPosition(
        attributes.getInt(R.styleable.dragger_layout_drag_position, DEFAULT_DRAG_POSITION));
    this.attributes = attributes;
  }

  private void configDragViewHelper() {
    dragHelperCallback = new DraggerHelperCallback(this, dragView, dragPosition, draggerHelperConfig);
    dragHelper = ViewDragHelper.create(this, SENSITIVITY, dragHelperCallback);
    dragHelperCallback.setDraggerListener(draggerListener);
  }

  public void setDraggerHelperListener(DraggerHelperListener draggerListener) {
    this.draggerListener = draggerListener;
    dragHelperCallback.setDraggerListener(draggerListener);
  }

  public void setDraggerCallback(DraggerCallback draggerCallback) {
    this.draggerCallback = draggerCallback;
  }

  private void mapGUI(TypedArray attributes) {
    if (getChildCount() == 2) {
      int dragViewId = attributes.getResourceId(
          R.styleable.dragger_layout_drag_view_id, R.id.drag_view);
      int shadowViewId = attributes.getResourceId(
          R.styleable.dragger_layout_shadow_view_id, R.id.shadow_view);
      dragView = findViewById(dragViewId);
      shadowView = findViewById(shadowViewId);
    } else {
      throw new IllegalStateException("DraggerView must contains only two direct child");
    }
  }

  private boolean isViewHit(View view, int x, int y) {
    int[] viewLocation = new int[2];
    view.getLocationOnScreen(viewLocation);
    int[] parentLocation = new int[2];
    this.getLocationOnScreen(parentLocation);
    int screenX = parentLocation[0] + x;
    int screenY = parentLocation[1] + y;
    return screenX >= viewLocation[0]
        && screenX < viewLocation[0] + view.getWidth()
        && screenY >= viewLocation[1]
        && screenY < viewLocation[1] + view.getHeight();
  }

  boolean isDragViewAboveTheMiddle() {
    int parentSize;
    float viewAxisPosition;
    switch (dragPosition) {
      case LEFT:
        parentSize = dragView.getWidth();
        viewAxisPosition = -ViewHelper.getX(dragView) + (parentSize * dragLimit);
        break;
      case RIGHT:
        parentSize = dragView.getWidth();
        viewAxisPosition = ViewHelper.getX(dragView) + (parentSize * dragLimit);
        break;
      case TOP:
      default:
        parentSize = dragView.getHeight();
        viewAxisPosition = ViewHelper.getY(dragView) + (parentSize * dragLimit);
        break;
      case BOTTOM:
        parentSize = dragView.getHeight();
        viewAxisPosition = -ViewHelper.getY(dragView) + (parentSize * dragLimit);
        break;
    }
    return parentSize < viewAxisPosition;
  }

  public void expandWithDelay() {
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        if (isEnabled()) {
          setViewAlpha(dragView, MAX_ALPHA);
          shadowView.setVisibility(VISIBLE);
          openActivity();
          canFinish = true;
        }
      }
    }, DELAY);
  }

  private void openActivity() {
    moveToCenter();
  }

  public void closeActivity() {
    if(dragPosition != null) {
      switch (dragPosition) {
        case LEFT:
          closeFromCenterToLeft();
          break;
        case RIGHT:
          closeFromCenterToRight();
          break;
        case TOP:
        default:
          closeFromCenterToBottom();
          break;
        case BOTTOM:
          closeFromCenterToTop();
          break;
      }
    } else {
      throw new IllegalStateException("dragPosition is null");
    }
  }

  public void moveToCenter() {
    smoothSlideTo(dragView, 0, 0);
    notifyOpen();
  }

  public void closeFromCenterToRight() {
    smoothSlideTo(dragView, (int) getHorizontalDragRange(), 0);
    notifyClosed();
  }

  public void closeFromCenterToLeft() {
    smoothSlideTo(dragView, (int) -getHorizontalDragRange(), 0);
    notifyClosed();
  }

  public void closeFromCenterToTop() {
    smoothSlideTo(dragView, 0, (int) -getVerticalDragRange());
    notifyClosed();
  }

  public void closeFromCenterToBottom() {
    smoothSlideTo(dragView, 0, (int) (SLIDE_IN * getVerticalDragRange()));
    notifyClosed();
  }

  private void notifyOpen() {
    if (draggerCallback != null) {
      draggerCallback.notifyOpen();
    }
  }

  private void notifyClosed() {
    if (draggerCallback != null) {
      draggerCallback.notifyClose();
    }
  }

  private void setViewAlpha(View view, float alpha) {
    ViewHelper.setAlpha(view, alpha);
  }

  private boolean smoothSlideTo(View view, int x, int y) {
    if (dragHelper.smoothSlideViewTo(view, x, y)) {
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }

  private DraggerHelperConfig draggerHelperConfig = new DraggerHelperConfig() {

    @Override public float dragVerticalDragRange() {
      return getVerticalDragRange();
    }

    @Override public float dragHorizontalDragRange() {
      return getHorizontalDragRange();
    }

  };

}
