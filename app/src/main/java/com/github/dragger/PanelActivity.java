package com.github.dragger;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.dragger.app.R;
import com.github.library.DraggerPanel;

public class PanelActivity extends ActionBarActivity {

  private LayoutInflater layoutInflater;

  @InjectView(R.id.dragger_panel) DraggerPanel draggerPanel;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_panel);
    ButterKnife.inject(this);
    configLayoutInflater();
  }

  @Override protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    draggerPanel.initializeView();
    draggerPanel.addViewOnDrag(layoutInflater.inflate(R.layout.layout_content, draggerPanel, false));
    draggerPanel.addViewOnShadow(layoutInflater.inflate(R.layout.layout_shadow, draggerPanel, false));
  }

  private void configLayoutInflater() {
    layoutInflater = LayoutInflater.from(this);
  }
}