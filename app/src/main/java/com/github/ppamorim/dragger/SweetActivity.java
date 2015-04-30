package com.github.ppamorim.dragger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.ppamorim.dragger.app.R;

public class SweetActivity extends DraggerSweetActivity {

  @InjectView(R.id.container) LinearLayout container;

  @OnClick(R.id.sweet_parent) void onSweetParent() {
    startActivity(new Intent(this, SweetParentActivity.class));
  }

  @Override protected View getRootView() {
    return container;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sweet);
    ButterKnife.inject(this);
  }

}
