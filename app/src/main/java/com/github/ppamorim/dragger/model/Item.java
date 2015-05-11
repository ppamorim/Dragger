package com.github.ppamorim.dragger.model;

import com.github.ppamorim.dragger.app.R;
import com.github.ppamorim.recyclerrenderers.interfaces.Renderable;

public class Item implements Renderable {

  private String text;

  public Item(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  @Override public int getRenderableResourceId(int i) {
    return R.layout.adapter_item;
  }

}
