package com.github.ppamorim.dragger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.ppamorim.dragger.app.R;
import java.util.List;

public class ItemListAdapter extends BaseAdapter {

  private List<String> data;
  private LayoutInflater layoutInflater;

  public ItemListAdapter(Context context, List<String> data) {
    this.layoutInflater = LayoutInflater.from(context);
    this.data = data;
  }

  @Override public int getCount() {
    return data.size();
  }

  @Override public String getItem(int position) {
    return data.get(position);
  }

  @Override public long getItemId(int position) {
    return getItem(position).hashCode();
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder holder;
    if (convertView != null) {
      holder = (ViewHolder) convertView.getTag();
    } else {
      convertView = layoutInflater.inflate(R.layout.adapter_item, parent, false);
      holder = new ViewHolder(convertView);
      convertView.setTag(holder);
    }

    holder.textView.setText(getItem(position));

    return convertView;

  }

  static class ViewHolder {
    @InjectView(R.id.text) TextView textView;
    public ViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }

}
