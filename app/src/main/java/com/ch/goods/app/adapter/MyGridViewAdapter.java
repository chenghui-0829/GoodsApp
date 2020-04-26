package com.ch.goods.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ch.goods.app.R;
import com.ch.goods.app.bean.GoodInfo;

import java.util.List;

public class MyGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<GoodInfo> goodsList;

    public MyGridViewAdapter(Context context, List<GoodInfo> list) {
        this.mContext = context;
        this.goodsList = list;
    }

    @Override
    public int getCount() {
        return goodsList.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(
                    mContext).inflate(R.layout.goods_grid_item_layout, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.good_grid_item_image);
            holder.priceText = convertView.findViewById(R.id.good_grid_item_price);
            holder.titleText = convertView.findViewById(R.id.good_grid_item_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(goodsList.get(position).getImageUrl()).into(holder.imageView);
        holder.priceText.setText("￥" + goodsList.get(position).getPrice());
        holder.titleText.setText(goodsList.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView priceText, titleText;
        ImageView imageView;
    }
}
