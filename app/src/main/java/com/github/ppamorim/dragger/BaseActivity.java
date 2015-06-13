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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import butterknife.InjectView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ppamorim.dragger.app.R;
import com.github.ppamorim.dragger.model.Home;
import com.github.ppamorim.dragger.renderers.factory.Factory;
import com.github.ppamorim.recyclerrenderers.adapter.RendererAdapter;
import com.github.ppamorim.recyclerrenderers.builder.RendererBuilder;
import com.github.ppamorim.recyclerrenderers.interfaces.Renderable;
import java.util.ArrayList;

public class BaseActivity extends AbstractToolbarActivity {

  @InjectView(R.id.recycler_view) ObservableRecyclerView observableRecyclerView;

  @Override protected String getToolbarTitle() {
    return getResources().getString(R.string.app_name);
  }

  @Override protected int getContentViewId() {
    return R.layout.activity_base;
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    String[] items = getResources().getStringArray(R.array.home);
    ArrayList<Renderable> renderables = new ArrayList<>(items.length);
    for (String text : items) {
      renderables.add(new Home(text));
    }
    observableRecyclerView.setHasFixedSize(true);
    observableRecyclerView.setItemAnimator(new DefaultItemAnimator());
    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    observableRecyclerView.setLayoutManager(layoutManager);
    observableRecyclerView.setAdapter(
        new RendererAdapter(renderables,
            new RendererBuilder(new Factory()),
            LayoutInflater.from(this)));
  }

  public void onItemClick(int position) {
    switch (position) {
      case 0:
        startDraggerActivity(DraggerPosition.LEFT);
        break;
      case 1:
        startDraggerActivity(DraggerPosition.RIGHT);
        break;
      case 2:
        startDraggerActivity(DraggerPosition.TOP);
        break;
      case 3:
        startDraggerActivity(DraggerPosition.BOTTOM);
        break;
      case 4:
        startActivityNoAnimation(new Intent(this, PanelActivity.class));
        break;
      case 5:
        startActivityNoAnimation(new Intent(this, DraggingActivity.class));
        break;
      case 6:
        startActivityNoAnimation(new Intent(this, EditTextActivity.class));
        break;
      case 7:
        startActivityNoAnimation(new Intent(this, ActivityListActivity.class));
        break;
      case 8:
        startActivityNoAnimation(new Intent(this, LazyActivity.class));
        break;
      default:
        break;
    }
  }

  private void startDraggerActivity(DraggerPosition dragPosition) {
    Intent intent = new Intent(this, ImageActivity.class);
    intent.putExtra(ImageActivity.DRAG_POSITION, dragPosition);
    startActivityNoAnimation(intent);
  }

  private void startActivityNoAnimation(Intent intent) {
    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    startActivity(intent);
  }

}
