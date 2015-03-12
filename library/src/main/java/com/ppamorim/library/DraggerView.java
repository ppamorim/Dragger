package com.ppamorim.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.nineoldandroids.view.ViewHelper;
import com.ppamorim.library.transformer.SlideTransformer;

public class DraggerView extends FrameLayout {

  private static final int MAX_ALPHA = 1;

  private TypedArray attributes;

  private SlideTransformer slideTransformer;

  private ViewDragHelper mDragHelper;
  private View mDragView;
  private View mShadowView;

  private static final float SLIDE_TOP = 0f;
  private static final float SLIDE_BOTTOM = 1f;

  public DraggerView(Context context) {
    this(context, null);
  }

  public DraggerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initializeAttributes(attrs);
  }

  private void initializeAttributes(AttributeSet attrs) {
    TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.dragger_layout);
    this.attributes = attributes;
  }

  private void configDragViewHelper() {
    mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback(this, mDragView, mShadowView, draggerListener));
  }

  private void mapGUI(TypedArray attributes) {
    int dragViewId =
        attributes.getResourceId(R.styleable.dragger_layout_drag_view_id, R.id.drag_view);
    int shadowViewId =
        attributes.getResourceId(R.styleable.dragger_layout_shadow_view_id, R.id.shadow_view);
    mDragView = findViewById(dragViewId);
    mShadowView = findViewById(shadowViewId);
  }

  private void initializeTransformer(TypedArray attributes) {
    slideTransformer = new SlideTransformer(mDragView, this);
  }


  boolean isDragViewAboveTheMiddle() {
    return slideTransformer.isAboveTheMiddle();
  }

  private float getVerticalDragRange() {
    return getHeight() - slideTransformer.getMinHeightPlusMargin();
  }

  public void expand() {
    smoothSlideTo(SLIDE_TOP);
    //notifyMaximizeToListener();
  }

  public void collapse() {
    smoothSlideTo(SLIDE_BOTTOM);
    //notifyMinimizeToListener();
  }

  private boolean smoothSlideTo(float slideOffset) {
    final int topBound = getPaddingTop();
    int y = (int) (topBound + slideOffset * getVerticalDragRange());
    if (mDragHelper.smoothSlideViewTo(mDragView, 0, y)) {
      ViewCompat.postInvalidateOnAnimation(this);
      return true;
    }
    return false;
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    if (!isInEditMode()) {
      mapGUI(attributes);
      initializeTransformer(attributes);
      attributes.recycle();
      configDragViewHelper();
    }
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    final int action = MotionEventCompat.getActionMasked(ev);
    if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
      mDragHelper.cancel();
      return false;
    }
    return mDragHelper.shouldInterceptTouchEvent(ev);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    mDragHelper.processTouchEvent(ev);
    return true;
  }

  @Override public void computeScroll() {
    if (!isInEditMode() && mDragHelper.continueSettling(true)) {
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }

  private DraggerListener draggerListener = new DraggerListener() {
    @Override public void onViewPositionChanged(float dragValue) {
      ViewHelper.setAlpha(mShadowView, MAX_ALPHA - dragValue);
    }

    @Override public float dragRange() {
      return getVerticalDragRange();
    }
  };

}
