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
package com.github.library.transformer;

import android.view.View;
import android.widget.RelativeLayout;
import com.nineoldandroids.view.ViewHelper;

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
