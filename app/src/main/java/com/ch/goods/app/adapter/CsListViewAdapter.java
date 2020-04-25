package com.ch.goods.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ch.goods.app.R;

import java.util.List;

public class CsListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mList;

    public CsListViewAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.cs_list_item_layout, parent, false);
        TextView textView = view.findViewById(R.id.cs_list_item_text);
        textView.setText(mList.get(position).replaceAll(" ", ""));
        return view;
    }
}
