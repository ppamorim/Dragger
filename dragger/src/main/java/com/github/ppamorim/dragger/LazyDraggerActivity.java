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

/**
 * Class created to provide dragger activity,
 * this is the most easy way to use library
 * with a delay or when call the show() method
 *
 * @author Pedro Paulo de Amorim
 */
public class LazyDraggerActivity extends BaseDraggerActivity {

  private LazyDraggerPanel lazyDraggerPanel;

  @Override public void setContentView(int layoutResID) {
    configDraggerView();
    configViews(layoutResID);
    super.setContentView(lazyDraggerPanel);
  }

  @Override public void onBackPressed() {
    lazyDraggerPanel.closeActivity();
  }

  public LazyDraggerPanel getDraggerPanel() {
    return lazyDraggerPanel;
  }

  private void configDraggerView() {
    lazyDraggerPanel = new LazyDraggerPanel(this);
    lazyDraggerPanel.initializeView();
  }

  private void configViews(int layoutResID) {
    lazyDraggerPanel.addViewOnDrag(inflateLayout(layoutResID));
    if (shadowResID == -1) {
      shadowResID = R.layout.layout_shadow;
    }
    lazyDraggerPanel.addViewOnShadow(inflateLayout(shadowResID));
  }

  public void setDraggerPosition(DraggerPosition dragPosition) {
    lazyDraggerPanel.setLazyDraggerPosition(dragPosition);
  }

  public void setDraggerLimit(float dragLimit) {
    lazyDraggerPanel.setLazyDraggerLimit(dragLimit);
  }

  public void setDraggerCallback(DraggerCallback draggerCallback) {
    lazyDraggerPanel.setLazyDraggerCallback(draggerCallback);
  }

  public void setSlideEnabled(boolean enabled) {
    lazyDraggerPanel.setSlideEnabled(enabled);
  }

  public void closeActivity() {
    lazyDraggerPanel.closeActivity();
  }

  public void show() {
    lazyDraggerPanel.show();
  }

  public void setAnimationDuration(int baseSettleDuration, int maxSettleDuration) {
    lazyDraggerPanel.setAnimationDuration(baseSettleDuration, maxSettleDuration);
  }

  public void setAnimationDuration(int miliseconds, float multipler) {
    lazyDraggerPanel.setAnimationDuration(miliseconds, multipler);
  }

}
