package com.github.ppamorim.dragger.renderers.viewholder;

import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.ppamorim.dragger.BaseActivity;
import com.github.ppamorim.dragger.app.R;
import com.github.ppamorim.dragger.model.Home;
import com.github.ppamorim.dragger.util.ViewUtil;
import com.github.ppamorim.recyclerrenderers.viewholder.RenderViewHolder;

public class ViewHolderHome extends RenderViewHolder<Home> implements View.OnClickListener {

  @InjectView(R.id.text) TextView textView;
  @InjectView(R.id.thumbnail) SimpleDraweeView simpleDraweeView;

  public ViewHolderHome(View view) {
    super(view);
    view.setOnClickListener(this);
    ButterKnife.inject(this, view);
  }

  @Override public void onClick(View view) {
    ((BaseActivity) getContext()).onItemClick(getAdapterPosition());
  }

  @Override public void onBindView(Home home) {
    if (home != null) {
      textView.setText(home.getText());
      ViewUtil.bind(simpleDraweeView,
          "https://github.com/ppamorim/Dragger/blob/master/art/app_image.jpg?raw=true");
    }
  }

}
