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

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import butterknife.InjectView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ppamorim.dragger.app.R;
import com.github.ppamorim.dragger.model.Item;
import com.github.ppamorim.dragger.renderers.factory.Factory;
import com.github.ppamorim.recyclerrenderers.adapter.RendererAdapter;
import com.github.ppamorim.recyclerrenderers.builder.RendererBuilder;
import com.github.ppamorim.recyclerrenderers.interfaces.Renderable;
import java.util.ArrayList;

public class ListActivity extends AbstractToolbarActivity {

  @InjectView(R.id.dragger_view) DraggerView draggerView;
  @InjectView(R.id.recycler_view) ObservableRecyclerView recyclerView;

  @Override protected String getToolbarTitle() {
    return getResources().getString(R.string.app_name);
  }

  @Override protected int getContentViewId() {
    return R.layout.activity_list;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    configRecyclerView();
  }

  @Override public void onBackPressed() {
    draggerView.close();
  }

  public void configRecyclerView() {

    ArrayList<Renderable> texts = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      texts.add(new Item(new StringBuilder("test ").append(i).toString()));
    }

    recyclerView.setHasFixedSize(true);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(new RendererAdapter(
        texts,
        new RendererBuilder(new Factory()),
        LayoutInflater.from(this)));
    recyclerView.setScrollViewCallbacks(onObservableScrollViewCallbacks);
  }

  private ObservableScrollViewCallbacks onObservableScrollViewCallbacks =
      new ObservableScrollViewCallbacks() {
    @Override public void onScrollChanged(int scrollY, boolean firstScroll,
        boolean dragging) {
      draggerView.setSlideEnabled(scrollY == 0);
    }
    @Override public void onDownMotionEvent() { }
    @Override public void onUpOrCancelMotionEvent(ScrollState scrollState) { }
  };

}
