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

/**
 * Class created to provide dragger panel,
 * this need to be used only with fragments
 * and do the same of LazyDraggerView
 *
 * @author Pedro Paulo de Amorim
 */
public class LazyDraggerPanel extends BaseDraggerPanel {

  private LazyDraggerView lazyDraggerView;

  public LazyDraggerPanel(Context context) {
    super(context);
  }

  public LazyDraggerPanel(Context context, AttributeSet attrs) {
    super(context, attrs);
    initializeAttributes(attrs);
  }

  public LazyDraggerPanel(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initializeAttributes(attrs);
  }

  public LazyDraggerView getLazyDraggerView() {
    return lazyDraggerView;
  }

  public void setLazyDraggerLimit(float draggerLimit) {
    lazyDraggerView.setDraggerLimit(draggerLimit);
  }

  public void setLazyDraggerPosition(DraggerPosition dragPosition) {
    lazyDraggerView.setDraggerPosition(dragPosition);
  }

  public void setLazyDraggerCallback(DraggerCallback draggerCallback) {
    lazyDraggerView.setDraggerCallback(draggerCallback);
  }

  public void config() {
    lazyDraggerView.setRunAnimationOnFinishInflate(false);
  }

  public void setSlideEnabled(boolean enabled) {
    lazyDraggerView.setSlideEnabled(enabled);
  }

  public void initializeView() {
    super.initializeView(R.layout.lazy_dragger_panel);
    lazyDraggerView = (LazyDraggerView) findViewById(R.id.dragger_view);
    if (attributes != null) {
      setLazyDraggerLimit(draggerLimit);
      setLazyDraggerPosition(DraggerPosition.getDragPosition(draggerPosition));
    }
    config();
  }

  public void closeActivity() {
    lazyDraggerView.closeActivity();
  }

  public void show() {
    lazyDraggerView.show();
  }

  public void setAnimationDuration(int baseSettleDuration, int maxSettleDuration) {
    lazyDraggerView.setAnimationDuration(baseSettleDuration, maxSettleDuration);
  }

  public void setAnimationDuration(int miliseconds, float multipler) {
    lazyDraggerView.setAnimationDuration(miliseconds, multipler);
  }

}
