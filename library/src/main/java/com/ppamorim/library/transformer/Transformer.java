package com.ppamorim.library.transformer;

import android.view.View;
import android.widget.RelativeLayout;
import com.nineoldandroids.view.ViewHelper;

/**
 * Abstract class created to be implemented by different classes are going to change the size of a
 * view. The most basic one is going to scale the view and the most complex used with VideoView is
 * going to change the size of the view.
 * <p/>
 * The view used in this class has to be contained by a RelativeLayout.
 * <p/>
 * This class also provide information about the size of the view and the position because
 * different Transformer implementations could change the size of the view but not the position,
 * like ScaleTransformer does.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
public abstract class Transformer {

  public static final float DRAG_LIMIT = 0.5f;

  private final View view;
  private final View parent;

  private int originalWidth;
  private int originalHeight;

  private float dragLimit = DRAG_LIMIT;

  public Transformer(View view, View parent) {
    this.view = view;
    this.parent = parent;
  }

  /**
   * Change view height using the LayoutParams of the view.
   *
   * @param newHeight to change
   */
  public void setViewHeight(int newHeight) {
    if (newHeight > 0) {
      originalHeight = newHeight;
      RelativeLayout.LayoutParams layoutParams =
          (RelativeLayout.LayoutParams) view.getLayoutParams();
      layoutParams.height = newHeight;
      view.setLayoutParams(layoutParams);
    }
  }

  protected View getView() {
    return view;
  }

  protected View getParentView() {
    return parent;
  }

  public float getDragLimit() {
    return dragLimit;
  }
  public void setDragLimit(float dragLimit) {
    this.dragLimit = dragLimit;
  }

  public abstract void updatePosition(float verticalDragOffset);

  /**
   * @return width of the view before it has change the size.
   */
  public int getOriginalWidth() {
    if (originalWidth == 0) {
      originalWidth = view.getMeasuredWidth();
    }
    return originalWidth;
  }

  /**
   * @return height of the view before it has change the size.
   */
  public int getOriginalHeight() {
    if (originalHeight == 0) {
      originalHeight = view.getMeasuredHeight();
    }
    return originalHeight;
  }

  public boolean isViewAtTop() {
    return view.getTop() == 0;
  }

  public boolean isAboveTheMiddle() {
    int parentHeight = parent.getHeight();
    float dragLimit = getDragLimit();
    float viewYPosition = ViewHelper.getY(view) + (view.getHeight() * dragLimit);
    return viewYPosition < (parentHeight * dragLimit);
  }

  public abstract boolean isViewAtBottom();

  public abstract int getMinHeightPlusMargin();

}
