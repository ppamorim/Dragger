package com.github.ppamorim.dragger;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import butterknife.InjectView;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ppamorim.dragger.app.R;
import com.github.ppamorim.dragger.model.Item;
import com.github.ppamorim.dragger.renderers.factory.Factory;
import com.github.ppamorim.recyclerrenderers.adapter.RendererAdapter;
import com.github.ppamorim.recyclerrenderers.builder.RendererBuilder;
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

  public  void configRecyclerView() {

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
    recyclerView.setScrollViewCallbacks(onObservableScrollViewCallbacks);
  }

  private ObservableScrollViewCallbacks onObservableScrollViewCallbacks =
      new ObservableScrollViewCallbacks() {
    @Override public void onScrollChanged(int scrollY, boolean firstScroll,
        boolean dragging) {
      draggerView.setCanSlide(!(scrollY == 0));
    }
    @Override public void onDownMotionEvent() {}
    @Override public void onUpOrCancelMotionEvent(ScrollState scrollState) {}
  };

}
