package com.ch.goods.app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ch.goods.app.adapter.MyGridViewAdapter;
import com.ch.goods.app.bean.GoodInfo;
import com.ch.goods.app.http.HttpRequest;
import com.ch.goods.app.http.HttpRequestUtil;
import com.ch.goods.app.http.RequestCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;
    private GridView goodsGridView;
    private MyGridViewAdapter mAdapter;
    private List<GoodInfo> goodsList;
    private SmartRefreshLayout refreshLayout;
    private int page = 1, pageSize = 20;
    private EditText editText;
    private String goodName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goodsGridView = findViewById(R.id.index_page_grid);
        refreshLayout = findViewById(R.id.index_page_refresh_layout);
        editText = findViewById(R.id.index_page_edit);
        refreshLayout.setEnableLoadMore(false);
        goodsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, GoodDetailsActivity.class);
                intent.putExtra("id", goodsList.get(position).getId());
                context.startActivity(intent);
            }
        });
        goodsList = new ArrayList<>();
        _getData(0);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                goodsList = new ArrayList<>();
                _getData(1);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                _getData(2);
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                goodName = s.toString();
                page = 1;
                goodsList = new ArrayList<>();
                _getData(0);
            }
        });
    }

    private void _getData(final int type) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", goodName);
        params.put("page", page);
        params.put("pagesize", pageSize);
        HttpRequestUtil.getInstance().get(context, HttpRequestUtil.URL, "GetProductList", params, new RequestCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(JSONObject result, String state, String msg, String data) {
                super.onSuccess(result, state, msg, data);
                JSONArray jsonArray = JSON.parseArray(data);
                List<GoodInfo> tempList = new ArrayList<>();
                if (jsonArray.size() < pageSize) {
                    refreshLayout.setEnableLoadMore(false);
                } else {
                    refreshLayout.setEnableLoadMore(true);
                }
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    GoodInfo model = new GoodInfo();
                    model.setId(object.getString("Id"));
                    model.setPrice(object.getDouble("Price"));
                    model.setTitle(object.getString("Title"));
                    model.setImageUrl("http://121.199.56.252" + object.getString("FilesPath"));
                    tempList.add(model);
                }
                goodsList.addAll(tempList);
                mAdapter = new MyGridViewAdapter(context, goodsList);
                goodsGridView.setAdapter(mAdapter);
            }


            @Override
            public void onErro(String erro) {
                super.onErro(erro);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (type == 1) {
                    refreshLayout.finishRefresh();
                } else if (type == 2) {
                    refreshLayout.finishLoadMore();
                }
            }
        });

    }

}
