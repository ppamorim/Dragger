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

public class ListActivity extends AbstractToolbarActivity {

  public static final String DRAG_POSITION = "drag_position";

  @InjectView(R.id.dragger_view) DraggerView draggerView;
  @InjectView(R.id.list_view) ListView listView;

  @Override protected String getToolbarTitle() {
    return getResources().getString(R.string.app_name);
  }

  @Override protected int getContentViewId() {
    return R.layout.activity_list;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    configListView();
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
