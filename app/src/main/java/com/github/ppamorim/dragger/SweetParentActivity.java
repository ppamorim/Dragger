package com.github.ppamorim.dragger;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.github.ppamorim.dragger.app.R;

public class SweetParentActivity extends DraggerSweetParentActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
  }

}
