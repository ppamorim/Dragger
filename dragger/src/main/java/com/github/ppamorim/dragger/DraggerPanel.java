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
import android.util.AttributeSet;
import android.view.View;

/**
 * Class created to extends a BaseDraggerPanel,
 * this need to be used only with fragments
 * and do the same of DraggerView
 *
 * @author Pedro Paulo de Amorim
 */
public class DraggerPanel extends BaseDraggerPanel {

  private DraggerView draggerView;

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

  public DraggerView getDraggerView() {
    return draggerView;
  }

  public void setDraggerLimit(float draggerLimit) {
    draggerView.setDraggerLimit(draggerLimit);
  }

  public void setDraggerPosition(DraggerPosition dragPosition) {
    draggerView.setDraggerPosition(dragPosition);
  }

  public void setTension(float tension) {
    draggerView.setTension(tension);
  }

  public void setFriction(float friction) {
    draggerView.setFriction(friction);
  }

  public void setDraggerCallback(DraggerCallback draggerCallback) {
    draggerView.setDraggerCallback(draggerCallback);
  }

  public void setSlideEnabled(boolean enabled) {
    draggerView.setSlideEnabled(enabled);
  }

  public void initializeView() {
    super.initializeView(R.layout.dragger_panel);
    draggerView = (DraggerView) findViewById(R.id.dragger_view);
    if (attributes != null) {
      setDraggerLimit(draggerLimit);
      setDraggerPosition(DraggerPosition.getDragPosition(draggerPosition));
    }
  }

  public void addViewOnDrag(View view) {
    eraseViewIfNeeded(dragView);
    dragView.addView(view);
  }

  public void closeActivity() {
    draggerView.closeActivity();
  }

  public void setAnimationDuration(int baseSettleDuration, int maxSettleDuration) {
    draggerView.setAnimationDuration(baseSettleDuration, maxSettleDuration);
  }

  public void setAnimationDuration(int miliseconds, float multipler) {
    draggerView.setAnimationDuration(miliseconds, multipler);
  }

}
