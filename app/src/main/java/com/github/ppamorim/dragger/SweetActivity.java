package com.github.ppamorim.dragger;

import android.content.Intent;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.ppamorim.dragger.app.R;

public class SweetActivity extends DraggerSweetActivity {

  @OnClick(R.id.sweet_parent) void onSweetParent() {
    startActivity(new Intent(this, SweetParentActivity.class));
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sweet);
    ButterKnife.inject(this);
  }

}
