package com.github.dragger;

import android.os.Bundle;
import com.github.dragger.app.R;
import com.github.library.DraggerActivity;

public class DraggingActivity extends DraggerActivity {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.layout_content);
  }

}
