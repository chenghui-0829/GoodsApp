package com.ch.goods.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ch.goods.app.R;
import com.ch.goods.app.http.HttpRequestUtil;

import java.util.List;

public class GoodImagePageViewAdapter extends PagerAdapter {

    private List<String> mList;

    public GoodImagePageViewAdapter(List<String> list) {
        this.mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        View inflate = View.inflate(container.getContext(), R.layout.good_image_layout, null);
        ImageView imageView = inflate.findViewById(R.id.good_details_image);
        Glide.with(container.getContext()).load("http://121.199.56.252" + mList.get(position)).into(imageView);
        container.addView(inflate);
        return inflate;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
