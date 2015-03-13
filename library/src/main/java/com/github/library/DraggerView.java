
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
package com.github.library;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.nineoldandroids.view.ViewHelper;

public class DraggerView extends FrameLayout {

  //private static final float COLLAPSE = 0f;
  //private static final float EXPAND = 1f;

  private static final int DELAY = 200;

  private static final float SLIDE_OUT = 0f;
  private static final float SLIDE_IN = 1f;

  private static final int MAX_ALPHA = 1;
  private static final int MIN_ALPHA = 0;

  private static final int DEFAULT_DRAG_POSITION = DragPosition.TOP.ordinal();
  private static final float SENSITIVITY = 1.0f;
  private static final float DEFAULT_DRAG_LIMIT = 0.5f;

  private boolean canFinish = false;
  private float verticalDragRange;
  private float horizontalDragRange;
  private float dragLimit;

  private TypedArray attributes;
  private DragPosition dragPosition;

  private DragHelperCallback mDragHelperCallback;
  private ViewDragHelper mDragHelper;
  private View mDragView;
  private View mShadowView;

  private Handler handler = new Handler();

  public DraggerView(Context context) {
    this(context, null);
  }

  public DraggerView(Context context, AttributeSet attrs) {
    super(context, attrs);
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
    if(mDragView != null) {
      mDragView.measure(measureWidth, measureHeight);
      setViewAlpha(mDragView, MIN_ALPHA);
      closeView();
      expandWithDelay();
    }
  }

  @Override protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    setVerticalDragRange(height);
    setHorizontalDragRange(width);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    final int action = MotionEventCompat.getActionMasked(ev);
    if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
      mDragHelper.cancel();
      return false;
    }
    return mDragHelper.shouldInterceptTouchEvent(ev);
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    mDragHelper.processTouchEvent(ev);
    return true;
  }

  @Override public void computeScroll() {
    if (!isInEditMode() && mDragHelper.continueSettling(true)) {
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

  public float getDragLimit() {
    return dragLimit;
  }

  public void setDragLimit(float dragLimit) {
    this.dragLimit = dragLimit;
  }

  public DragPosition getDragPosition() {
    return dragPosition;
  }

  public void setDragPosition(DragPosition dragPosition) {
    this.dragPosition = dragPosition;
    mDragHelperCallback.setDragPosition(dragPosition);
  }

  private void initializeAttributes(AttributeSet attrs) {
    TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.dragger_layout);
    this.dragLimit = attributes.getFloat(R.styleable.dragger_layout_drag_limit,
        DEFAULT_DRAG_LIMIT);
    this.dragPosition = getDragPosition(attributes.getInt(R.styleable.dragger_layout_drag_position,
        DEFAULT_DRAG_POSITION));
    this.attributes = attributes;
  }

  private DragPosition getDragPosition(int position) {
    switch (position) {
      case 0:
        return DragPosition.LEFT;
      case 1:
        return DragPosition.RIGHT;
      case 3:
        return DragPosition.BOTTOM;
      case 2:
      default:
        return DragPosition.TOP;
    }
  }

  private void configDragViewHelper() {
    mDragHelperCallback = new DragHelperCallback(this, mDragView, dragPosition, draggerListener);
    mDragHelper = ViewDragHelper.create(this, SENSITIVITY, mDragHelperCallback);
  }

  private void mapGUI(TypedArray attributes) {
    int dragViewId =
        attributes.getResourceId(R.styleable.dragger_layout_drag_view_id, R.id.drag_view);
    int shadowViewId =
        attributes.getResourceId(R.styleable.dragger_layout_shadow_view_id, R.id.shadow_view);
    mDragView = findViewById(dragViewId);
    mShadowView = findViewById(shadowViewId);
  }

  boolean isDragViewAboveTheMiddle() {
    int parentSize;
    float viewAxisPosition;
    switch (dragPosition) {
      case LEFT:
        parentSize = mDragView.getWidth();
        viewAxisPosition = ViewHelper.getX(mDragView);
        break;
      case RIGHT:
        parentSize = mDragView.getWidth();
        viewAxisPosition = ViewHelper.getX(mDragView) + (parentSize * dragLimit);
        break;
      case TOP:
      default:
        parentSize = mDragView.getHeight();
        viewAxisPosition = ViewHelper.getY(mDragView) + (parentSize * dragLimit);
        break;
      case BOTTOM:
        parentSize = -mDragView.getHeight();
        viewAxisPosition = ViewHelper.getY(mDragView) - (-parentSize * dragLimit);
        break;
    }
    return parentSize < viewAxisPosition;
  }

  public void expandWithDelay() {
    handler.postDelayed(new Runnable() {
      @Override public void run() {
        if(isEnabled()) {
          setViewAlpha(mDragView, MAX_ALPHA);
          mShadowView.setVisibility(VISIBLE);
          openView();
          canFinish = true;
        }
      }
    }, DELAY);
  }

  private void openView() {
    switch (dragPosition) {
      case LEFT:
        openFromSideToCenter();
        break;
      case RIGHT:
        openFromSideToCenter();
        break;
      case TOP:
      default:
        openFromBottomToTop();
        break;
      case BOTTOM:
        openFromSideToCenter();
        break;
    }
  }

  private void closeView() {
    switch (dragPosition) {
      case LEFT:
        closeFromRightToLeft();
        break;
      case RIGHT:
        closeFromLeftToRight();
        break;
      case TOP:
      default:
        closeFromTopToBottom();
        break;
      case BOTTOM:
        closeFromBottomToTop();
        break;
    }
  }

  public void closeFromLeftToRight() {
    if (mDragHelper.smoothSlideViewTo(mDragView, (int) getHorizontalDragRange(), 0)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  public void openFromSideToCenter() {
    if (mDragHelper.smoothSlideViewTo(mDragView, 0, 0)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  public void closeFromRightToLeft() {
    if (mDragHelper.smoothSlideViewTo(mDragView, (int) -getHorizontalDragRange(), 0)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }


  public void openFromBottomToTop() {
    smoothSlideToY(mDragView, SLIDE_OUT);
    //notifyMaximizeToListener();
  }

  public void closeFromTopToBottom() {
    smoothSlideToY(mDragView, SLIDE_IN);
    //notifyMinimizeToListener();
  }

  public void closeFromBottomToTop() {
    if (mDragHelper.smoothSlideViewTo(mDragView, 0,(int) -getVerticalDragRange())) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  public void setViewAlpha(View view, float alpha) {
    ViewHelper.setAlpha(view, alpha);
  }

  private boolean smoothSlideToY(View view, float slideOffset) {
    int y = (int) (slideOffset * getVerticalDragRange());
    if (mDragHelper.smoothSlideViewTo(view, 0, y)) {
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }

  private boolean smoothSlideToX(View view, boolean invertDragRange, float slideOffset) {
    final int leftBound = getPaddingLeft();
    int x = (int) (leftBound + slideOffset * getHorizontalDragRange());
    if (mDragHelper.smoothSlideViewTo(view, x, 0)) {
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }


  private void finish() {
    if(canFinish) {
      Context context = getContext();
      if (context instanceof Activity) {
        Activity act = (Activity) context;
        act.finish();
        act.overridePendingTransition(0, android.R.anim.fade_out);
      }
    }
  }

  private DraggerListener draggerListener = new DraggerListener() {

    @Override public void finishActivity() {
      finish();
    }

    @Override public void onViewPositionChanged(float dragValue) {
      ViewHelper.setAlpha(mShadowView, MAX_ALPHA - dragValue);
    }

    @Override public float dragVerticalDragRange() {
      return getVerticalDragRange();
    }

    @Override public float dragHorizontalDragRange() {
      return getHorizontalDragRange();
    }

    @Override public int dragViewWidth() {
      return mDragView.getWidth();
    }

  };

}
