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
package com.github.ppamorim.dragger.renderers.viewholder;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.ppamorim.dragger.app.R;
import com.github.ppamorim.dragger.model.Item;
import com.github.ppamorim.recyclerrenderers.viewholder.RenderViewHolder;

public class ViewHolderItem extends RenderViewHolder<Item> {

  @InjectView(R.id.text) TextView textView;

  public ViewHolderItem(View view) {
    super(view);
    ButterKnife.inject(this, view);
  }

  @Override public void onBindView(Item item) {
    if (item != null) {
      textView.setText(item.getText());
    }
  }

}
