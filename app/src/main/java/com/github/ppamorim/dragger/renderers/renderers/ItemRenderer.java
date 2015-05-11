package com.github.ppamorim.dragger.renderers.renderers;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.github.ppamorim.dragger.renderers.viewholder.ViewHolderItem;
import com.github.ppamorim.recyclerrenderers.renderer.Renderer;
import com.github.ppamorim.recyclerrenderers.viewholder.RenderViewHolder;

public class ItemRenderer extends Renderer {

  LayoutInflater layoutInflater;

  public ItemRenderer(int id) {
    super(id);
  }

  @Override public RenderViewHolder onCreateViewHolder(ViewGroup viewGroup, int id) {
    if(layoutInflater == null) {
      layoutInflater = LayoutInflater.from(viewGroup.getContext());
    }
    return new ViewHolderItem(layoutInflater.inflate(id, viewGroup, false));
  }

}
