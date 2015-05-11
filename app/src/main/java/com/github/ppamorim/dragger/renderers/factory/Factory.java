package com.github.ppamorim.dragger.renderers.factory;

import com.github.ppamorim.dragger.app.R;
import com.github.ppamorim.dragger.renderers.renderers.ItemRenderer;
import com.github.ppamorim.recyclerrenderers.interfaces.RendererFactory;
import com.github.ppamorim.recyclerrenderers.renderer.Renderer;

public class Factory implements RendererFactory {

  @Override public Renderer getRenderer(int id) {
    switch (id) {
      case R.layout.adapter_item:
        return new ItemRenderer(id);
      default:
        return null;
    }

  }

}
