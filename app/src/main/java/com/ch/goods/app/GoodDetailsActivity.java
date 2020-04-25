package com.ch.goods.app;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ch.goods.app.adapter.CsListViewAdapter;
import com.ch.goods.app.adapter.GoodImagePageViewAdapter;
import com.ch.goods.app.adapter.MyGridViewAdapter;
import com.ch.goods.app.bean.GoodInfo;
import com.ch.goods.app.http.HttpRequestUtil;
import com.ch.goods.app.http.RequestCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodDetailsActivity extends AppCompatActivity {

    private Context mContext = GoodDetailsActivity.this;
    private ViewPager mViewPager;
    private ListView mListView;
    private GoodImagePageViewAdapter pageViewAdapter;
    private CsListViewAdapter csListViewAdapter;
    private TextView titleText, priceText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_details);
        mViewPager = findViewById(R.id.good_details_image_pager);
        mListView = findViewById(R.id.good_details_cs_list_view);
        titleText = findViewById(R.id.good_details_title);
        priceText = findViewById(R.id.good_details_price);
        Intent intent = getIntent();
        _getData(intent.getStringExtra("id"));
    }

    private void _getData(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("Id", id);
        HttpRequestUtil.getInstance().get(mContext, HttpRequestUtil.URL, "GetProductListDetail", params, new RequestCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject result, String state, String msg, String data) {
                super.onSuccess(result, state, msg, data);
                JSONObject object = JSON.parseObject(data);
                titleText.setText(object.getString("Title"));
                priceText.setText("￥" + object.getString("Price"));
                String csString = object.getString("Describe");
                List<String> csList = Arrays.asList(csString.split("&"));
                csListViewAdapter = new CsListViewAdapter(mContext, csList);
                mListView.setAdapter(csListViewAdapter);

                List<String> imageList = new ArrayList<>();
                String mainImagePath = object.getString("MainFilesPath");
                imageList.add(mainImagePath);
                String otherImagePath = object.getString("FilesPath");
                if (!otherImagePath.equals("")) {
                    List<String> tempList = Arrays.asList(otherImagePath.split(","));
                    imageList.addAll(tempList);
                }
                pageViewAdapter = new GoodImagePageViewAdapter(imageList);
                mViewPager.setAdapter(pageViewAdapter);
            }

            @Override
            public void onErro(String erro) {
                super.onErro(erro);
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });

    }

}
