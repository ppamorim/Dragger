package com.github.library;

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
import com.github.library.transformer.SlideTransformer;

public class DraggerView extends FrameLayout {

  private static final float SLIDE_TOP = 0f;
  private static final float SLIDE_BOTTOM = 1f;
  private static final int MAX_ALPHA = 1;

  private float verticalDragRange;

  private TypedArray attributes;

  private SlideTransformer slideTransformer;

  private ViewDragHelper mDragHelper;
  private View mDragView;
  private View mShadowView;

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
    mDragHelper = ViewDragHelper.create(this, 1.0f,
        new DragHelperCallback(this, mDragView, mShadowView, draggerListener));
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
    int parentHeight = mDragView.getHeight();
    float dragLimit = 0.5f;
    float viewYPosition = ViewHelper.getY(mDragView) + (mDragView.getHeight() * dragLimit);
    return viewYPosition < (parentHeight * dragLimit);
  }

  private float getVerticalDragRange() {
    return verticalDragRange;
  }

  private void setVerticalDragRange(float verticalDragRange) {
    this.verticalDragRange = verticalDragRange;
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
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    int width = getMeasuredWidth();
    int height = getMeasuredHeight();
    int childWidth = width - getPaddingLeft() - getPaddingRight();
    int childHeight = height - getPaddingTop() - getPaddingBottom();
    int childLeft = getPaddingLeft();
    int childTop = getPaddingTop();
    int childRight = childLeft + childWidth;
    int childBottom = childTop + childHeight;
    mDragView.layout(childLeft, childTop, childRight, childBottom);
  }
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int measureWidth = MeasureSpec.makeMeasureSpec(
        getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
        MeasureSpec.EXACTLY);
    int measureHeight = MeasureSpec.makeMeasureSpec(
        getMeasuredHeight() - getPaddingTop() - getPaddingBottom(),
        MeasureSpec.EXACTLY);
    mDragView.measure(measureWidth, measureHeight);
  }

  @Override
  protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    setVerticalDragRange(height);
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

    @Override public float dragVerticalDragRange() {
      return getVerticalDragRange();
    }
  };

}
