package com.github.dragger;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.dragger.app.R;
import com.github.library.DraggerPanel;

public class PanelActivity extends ActionBarActivity {

  @InjectView(R.id.dragger_panel) DraggerPanel draggerPanel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_panel);
    ButterKnife.inject(this);
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
  }
}