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
 * Class created to provide a lazy expand
 * this will show the view when the method
 * show() is called
 *
 * @author Pedro Paulo de Amorim
 */
public class LazyDraggerView extends DraggerView {

  public LazyDraggerView(Context context) {
    super(context);
    config();
  }

  public LazyDraggerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    config();
  }

  public LazyDraggerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    config();
  }

  public void config() {
    setRunAnimationOnFinishInflate(false);
  }

}
