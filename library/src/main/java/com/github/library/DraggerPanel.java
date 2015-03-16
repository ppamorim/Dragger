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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class DraggerPanel extends FrameLayout {

  private static final float DEFAULT_DRAG_LIMIT = 0.5f;
  private static final int DEFAULT_DRAG_POSITION = DraggerPosition.TOP.ordinal();

  private float dragLimit;
  private DraggerPosition dragPosition;

  private TypedArray attributes;

  private DraggerView draggerView;
  private FrameLayout dragView;
  private FrameLayout shadowView;

  public DraggerPanel(Context context) {
    super(context);
  }

  public DraggerPanel(Context context, AttributeSet attrs) {
    super(context, attrs);
    initializeAttributes(attrs);
  }

  public DraggerPanel(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initializeAttributes(attrs);
  }

  public void setDraggerLimit(float draggerLimit) {
    draggerView.setDraggerLimit(draggerLimit);
  }

  public void setDraggerPosition(DraggerPosition dragPosition) {
    draggerView.setDraggerPosition(dragPosition);
  }

  public void setDraggerCallback(DraggerCallback draggerCallback) {
    draggerView.setDraggerCallback(draggerCallback);
  }

  private void initializeAttributes(AttributeSet attrs) {
    TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.dragger_layout);
    this.dragLimit = attributes.getFloat(R.styleable.dragger_layout_drag_limit, DEFAULT_DRAG_LIMIT);
    this.dragPosition = DraggerPosition.getDragPosition(
        attributes.getInt(R.styleable.dragger_layout_drag_position, DEFAULT_DRAG_POSITION));
    this.attributes = attributes;
  }

  /**
   * Apply all the custom view configuration and inflate the main view. The view won't be
   * visible if this method is not called.
   */
  public void initializeView() {
    inflate(getContext(), R.layout.dragger_panel, this);
    draggerView = (DraggerView) findViewById(R.id.dragger_view);
    dragView = (FrameLayout) findViewById(R.id.drag_view);
    shadowView = (FrameLayout) findViewById(R.id.shadow_view);
  }

  public void addViewOnDrag(View view) {
    eraseViewIfNeeded(dragView);
    dragView.addView(view);
  }

  public void addViewOnShadow(View view) {
    eraseViewIfNeeded(shadowView);
    shadowView.addView(view);
  }

  private void eraseViewIfNeeded(FrameLayout frameLayout) {
    if (frameLayout.getChildCount() > 0) {
      frameLayout.removeAllViews();
    }
  }

}
