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
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class DraggerSweetActivity extends DraggerActivity{

  public static final String BACKGROUND = "background";

  @Override public void startActivity(Intent intent) {
    try {
      Bitmap bitmap = ImageUtil.captureScreenShot(getRootView());
      String filename = "bitmap.png";
      FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
      stream.close();
      bitmap.recycle();
      intent.putExtra(BACKGROUND, filename);
      super.startActivity(intent);
    } catch (FileNotFoundException filee) {
      filee.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  protected abstract View getRootView();

}

