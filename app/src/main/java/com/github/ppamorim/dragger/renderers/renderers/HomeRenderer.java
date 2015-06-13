package com.github.ppamorim.dragger.renderers.renderers;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.github.ppamorim.dragger.renderers.viewholder.ViewHolderHome;
import com.github.ppamorim.recyclerrenderers.renderer.Renderer;
import com.github.ppamorim.recyclerrenderers.viewholder.RenderViewHolder;

public class HomeRenderer extends Renderer {
  @Override public RenderViewHolder onCreateViewHolder(ViewGroup viewGroup,
      LayoutInflater layoutInflater, int id) {
    return new ViewHolderHome(layoutInflater.inflate(id, viewGroup, false));
  }
}
