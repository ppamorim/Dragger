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

import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;

public class DraggerActivity extends ActionBarActivity {

  private int shadowResID = -1;
  private DraggerPanel draggerPanel;

  @Override public void setContentView(int layoutResID) {
    configDraggerView();
    configViews(layoutResID);
    super.setContentView(draggerPanel);
  }

  public void setShadowView(int shadowResID) {
    this.shadowResID = shadowResID;
  }

  private void configDraggerView() {
    draggerPanel = new DraggerPanel(this);
    draggerPanel.initializeView();
  }

  private void configViews(int layoutResID) {
    draggerPanel.addViewOnDrag(inflateLayout(layoutResID));
    if (shadowResID == -1) {
      shadowResID = R.layout.layout_shadow;
    }
    draggerPanel.addViewOnShadow(inflateLayout(shadowResID));
  }

  private View inflateLayout(int layoutResID) {
    return LayoutInflater.from(this).inflate(layoutResID, null);
  }

  public void setDraggerPosition(DraggerPosition dragPosition) {
    draggerPanel.setDraggerPosition(dragPosition);
  }

  public void setDraggerLimit(float dragLimit) {
    draggerPanel.setDraggerLimit(dragLimit);
  }

  public void setDraggerCallback(DraggerCallback draggerCallback) {
    draggerPanel.setDraggerCallback(draggerCallback);
  }

}
