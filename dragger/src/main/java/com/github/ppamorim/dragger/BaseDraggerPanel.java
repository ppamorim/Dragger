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
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * The base implementation of DraggerPanel.\
 */
public class BaseDraggerPanel extends FrameLayout {

  private static final float DEFAULT_DRAG_LIMIT = 0.5f;
  private static final int DEFAULT_DRAG_POSITION = DraggerPosition.TOP.ordinal();

  public float draggerLimit;
  public int draggerPosition;

  public TypedArray attributes;

  public FrameLayout dragView;
  public FrameLayout shadowView;

  public BaseDraggerPanel(Context context) {
    super(context);
  }

  public BaseDraggerPanel(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public BaseDraggerPanel(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  /**
   * Apply all the custom view configuration and inflate the main view. The view won't be
   * visible if this method is not called.
   */
  public void initializeView(int layoutId) {
    inflate(getContext(), layoutId, this);
    dragView = (FrameLayout) findViewById(R.id.drag_view);
    shadowView = (FrameLayout) findViewById(R.id.shadow_view);
  }

  /**
   * Add a custom view to shadowView, optional.
   *
   * @param view Instance of the view.
   */
  public void addViewOnShadow(View view) {
    eraseViewIfNeeded(shadowView);
    shadowView.addView(view);
  }

  /**
   * Add a custom view to dragView.
   *
   * @param view Instance of view.
   */
  public void addViewOnDrag(View view) {
    eraseViewIfNeeded(dragView);
    dragView.addView(view);
  }

  /**
   * Removes the view if needed, prevent some exceptions.
   *
   * @param view Instance of ViewGroup, every view
   * here extends this class.
   */
  public void eraseViewIfNeeded(ViewGroup view) {
    if (view.getChildCount() > 0) {
      view.removeAllViews();
    }
  }

  /**
   * Map the attributes to use with DraggerView
   *
   * @param attrs Attributes to be mapped.
   */
  public void initializeAttributes(AttributeSet attrs) {
    attributes = getContext().obtainStyledAttributes(attrs, R.styleable.dragger_layout);
    if (attributes != null) {
      draggerLimit = attributes.getFloat(R.styleable.dragger_layout_drag_limit, DEFAULT_DRAG_LIMIT);
      draggerPosition =
          attributes.getInt(R.styleable.dragger_layout_drag_position, DEFAULT_DRAG_POSITION);
    }
  }

}
