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
