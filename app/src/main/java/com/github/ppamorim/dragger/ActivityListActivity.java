package com.github.ppamorim.dragger;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ppamorim.dragger.model.Item;
import com.github.ppamorim.dragger.renderers.factory.Factory;
import com.github.ppamorim.recyclerrenderers.adapter.RendererAdapter;
import com.github.ppamorim.recyclerrenderers.builder.RendererBuilder;
import java.util.ArrayList;
import com.github.ppamorim.dragger.app.R;

public class ActivityListActivity extends DraggerActivity {

  @InjectView(R.id.recycler_view) ObservableRecyclerView recyclerView;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_activity);
    ButterKnife.inject(this);
    configRecyclerView();
  }

  public void configRecyclerView() {

    ArrayList<Item> texts = new ArrayList<>();
    for (int i = 0; i < 100; i++) {
      texts.add(new Item(new StringBuilder("test ").append(i).toString()));
    }

    recyclerView.setHasFixedSize(true);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(new RendererAdapter(texts, new RendererBuilder(new Factory())));
    recyclerView.setScrollViewCallbacks(observableScrollViewCallbacks);
  }

  private ObservableScrollViewCallbacks observableScrollViewCallbacks =
    new ObservableScrollViewCallbacks() {
      @Override public void onScrollChanged(int scrollY, boolean firstScroll,
          boolean dragging) {
        setSlideEnabled(scrollY != 0);
      }
      @Override public void onDownMotionEvent() { }
      @Override public void onUpOrCancelMotionEvent(ScrollState scrollState) { }
    };

}
