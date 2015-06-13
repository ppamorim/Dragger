package com.github.ppamorim.dragger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

public class BaseDraggerActivity extends AppCompatActivity {

  public int shadowResID = -1;

  public void setShadowView(int shadowResID) {
    this.shadowResID = shadowResID;
  }

  public View inflateLayout(int layoutResID) {
    return LayoutInflater.from(this).inflate(layoutResID, null);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    overridePendingTransition(0, 0);
    super.onCreate(savedInstanceState);
  }

  @Override protected void onPause() {
    overridePendingTransition(0, 0);
    super.onPause();
  }

}
