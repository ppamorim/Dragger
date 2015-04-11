package com.github.ppamorim.dragger;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.ppamorim.dragger.adapter.ItemListAdapter;
import com.github.ppamorim.dragger.app.R;
import com.github.ppamorim.library.DraggerPosition;
import com.github.ppamorim.library.DraggerView;
import java.util.ArrayList;

public class ListActivity extends ActionBarActivity {

  public static final String DRAG_POSITION = "drag_position";

  private Resources mResources;

  @InjectView(R.id.toolbar) Toolbar toolbar;
  @InjectView(R.id.dragger_view) DraggerView draggerView;
  @InjectView(R.id.list_view) ListView listView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    ButterKnife.inject(this);
    configResources();
    configToolbar();
    configListView();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        onBackPressed();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onBackPressed() {
    draggerView.closeActivity();
  }

  private void configResources() {
    mResources = getResources();
  }

  private void configToolbar() {
    setSupportActionBar(toolbar);
    toolbar.setTitle(mResources.getString(R.string.app_name));
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void configListView() {
    ArrayList<String> texts = new ArrayList<>();
    for(int i = 0; i < 100; i++) {
      texts.add(new StringBuilder("test ").append(i).toString());
    }
    listView.setAdapter(new ItemListAdapter(this, texts));
    draggerView.setListViewPosition(listView);
  }

}
