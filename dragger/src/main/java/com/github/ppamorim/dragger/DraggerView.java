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

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;

/**
 * Class created to extends a FrameLayout, that's a root of the view
 * It contains a dragView(the content) and a shadowView(for dim)
 * You must add this elements, otherwise
 *
 * @author Pedro Paulo de Amorim
 */
public class DraggerView extends FrameLayout {

  private static final int DELAY = 100;
  private static final float SLIDE_IN = 1f;

  private static final int MAX_ALPHA = 1;

  private static final int DEFAULT_TENSION = 40;
  private static final int DEFAULT_FRICTION = 6;

  private static final float SENSITIVITY = 1.0f;
  private static final float DEFAULT_DRAG_LIMIT = 0.5f;
  private static final int DEFAULT_DRAG_POSITION = DraggerPosition.TOP.getPosition();
  private static final int INVALID_POINTER = -1;

  private boolean runAnimationOnFinishInflate = true;
  private boolean animationFinish = false;
  private boolean canSlide = true;
  private int activePointerId = INVALID_POINTER;
  private float verticalDragRange;
  private float horizontalDragRange;
  private float dragLimit;
  private float tension;
  private float friction;
  private float progress;
  private double val;

  private TypedArray attributes;
  private DraggerPosition dragPosition;

  private DraggerCallback draggerCallback;
  private DraggerHelperCallback dragHelperCallback;
  private ViewDragHelper dragHelper;
  private View dragView;
  private View shadowView;

  private static volatile Spring singleton = null;

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
      preparePosition();
    }
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    getSpring().addListener(springListener);
  }

  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    getSpring().removeListener(springListener);
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
    }

  }

  @Override protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    setVerticalDragRange(height);
    setHorizontalDragRange(width);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (!isEnabled() || !canSlide()) {
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

  public void setRunAnimationOnFinishInflate(boolean runAnimationOnFinishInflate) {
    this.runAnimationOnFinishInflate = runAnimationOnFinishInflate;
  }

  public boolean getCanAnimate() {
    return !animationFinish;
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

  public boolean canSlide() {
    return canSlide;
  }

  public void setSlideEnabled(boolean canSlide) {
    this.canSlide = canSlide;
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
  }

  public void setTension(float tension) {
    this.tension = tension;
  }

  public void setFriction(float friction) {
    this.friction = friction;
  }

  private void initializeAttributes(AttributeSet attrs) {
    TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.dragger_layout);
    this.dragLimit = attributes.getFloat(R.styleable.dragger_layout_drag_limit,
        DEFAULT_DRAG_LIMIT);
    this.dragPosition = DraggerPosition.getDragPosition(
        attributes.getInt(R.styleable.dragger_layout_drag_position, DEFAULT_DRAG_POSITION));
    this.tension = attributes.getInteger(R.styleable.dragger_layout_tension, DEFAULT_TENSION);
    this.friction = attributes.getInteger(R.styleable.dragger_layout_friction, DEFAULT_FRICTION);
    this.attributes = attributes;
  }

  private void configDragViewHelper() {
    dragHelperCallback = new DraggerHelperCallback(this, dragView, dragPosition, draggerListener);
    dragHelper = ViewDragHelper.create(this, SENSITIVITY, dragHelperCallback);
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

  /**
   * The global default {@link Spring} instance.
   *
   * This instance is automatically initialized with defaults that are suitable to most
   * implementations.
   *
   */
  private Spring getSpring() {
    if (singleton == null) {
      synchronized (Spring.class) {
        if (singleton == null) {
          singleton = SpringSystem
              .create()
              .createSpring()
              .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction));
        }
      }
    }
    return singleton;
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
        viewAxisPosition = -ViewCompat.getX(dragView) + (parentSize * dragLimit);
        break;
      case RIGHT:
        parentSize = dragView.getWidth();
        viewAxisPosition = ViewCompat.getX(dragView) + (parentSize * dragLimit);
        break;
      case TOP:
      default:
        parentSize = dragView.getHeight();
        viewAxisPosition = ViewCompat.getY(dragView) + (parentSize * dragLimit);
        break;
      case BOTTOM:
        parentSize = dragView.getHeight();
        viewAxisPosition = -ViewCompat.getY(dragView) + (parentSize * dragLimit);
        break;
    }
    return parentSize < viewAxisPosition;
  }

  public void preparePosition() {
    post(new Runnable() {
      @Override public void run() {
        getSpring().setCurrentValue(1).setAtRest();
        if (runAnimationOnFinishInflate) {
          show();
        }
      }
    });
  }

  public void show() {
    new Handler().postDelayed(new Runnable() {
      @Override public void run() {
        getSpring().setEndValue(0);
      }
    }, DELAY);
  }

  public void closeActivity() {
    if (dragPosition != null) {
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

  private boolean smoothSlideTo(View view, int x, int y) {
    if (dragHelper.smoothSlideViewTo(view, x, y)) {
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }

  private void finish() {
    Context context = getContext();
    if (context instanceof Activity) {
      Activity activity = (Activity) context;
      if (!activity.isFinishing()) {
        activity.overridePendingTransition(0, android.R.anim.fade_out);
        activity.finish();
      }
      activity = null;
    }
    context = null;
    System.gc();
  }

  public void setAnimationDuration(int baseSettleDuration, int maxSettleDuration) {
    dragHelper.setBaseSettleDuration(baseSettleDuration);
    dragHelper.setMaxSettleDuration(maxSettleDuration);
  }

  public void setAnimationDuration(int miliseconds, float multipler) {
    dragHelper.setBaseSettleDuration(miliseconds);
    dragHelper.setMaxSettleDuration((int) (miliseconds * multipler));
  }

  private SpringListener springListener = new SpringListener() {
    @Override public void onSpringUpdate(Spring spring) {

      val = spring.getCurrentValue();
      switch (dragPosition) {
        case LEFT:
          progress = (float) SpringUtil.mapValueFromRangeToRange(val, 0, 1, 0, -dragView.getWidth());
          ViewCompat.setTranslationX(dragView, progress);
          break;
        case RIGHT:
          progress = (float) SpringUtil.mapValueFromRangeToRange(val, 0, 1, 0, dragView.getWidth());
          ViewCompat.setTranslationX(dragView, progress);
          break;
        case TOP:
          progress = (float) SpringUtil.mapValueFromRangeToRange(val, 0, 1, 0, dragView.getHeight());
          ViewCompat.setTranslationY(dragView, progress);
          break;
        case BOTTOM:
          progress = (float) SpringUtil.mapValueFromRangeToRange(val, 0, 1, 0, -dragView.getHeight());
          ViewCompat.setTranslationY(dragView, progress);
          break;
        default:
          break;
      }

      ViewCompat.setAlpha(shadowView,
          (float) (MAX_ALPHA - SpringUtil.mapValueFromRangeToRange(val, 0, 1, 0, 1)));

      if (draggerCallback != null) {
        draggerCallback.onProgress(spring.getCurrentValue());
      }
    }

    @Override public void onSpringAtRest(Spring spring) {
      if (draggerCallback != null) {
        draggerCallback.notifyOpen();
      }
    }

    @Override public void onSpringActivate(Spring spring) { }
    @Override public void onSpringEndStateChange(Spring spring) { }
  };

  private DraggerHelperListener draggerListener = new DraggerHelperListener() {

    @Override public void finishActivity() {
      finish();
    }

    @Override public void onViewPositionChanged(float dragValue) {
      ViewCompat.setAlpha(shadowView, MAX_ALPHA - dragValue);
    }

    @Override public float dragVerticalDragRange() {
      return getVerticalDragRange();
    }

    @Override public float dragHorizontalDragRange() {
      return getHorizontalDragRange();
    }

  };

}
