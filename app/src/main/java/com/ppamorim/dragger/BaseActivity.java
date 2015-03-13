package com.ppamorim.dragger;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.ppamorim.dragger.app.R;

public class BaseActivity extends ActionBarActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
  }
}
