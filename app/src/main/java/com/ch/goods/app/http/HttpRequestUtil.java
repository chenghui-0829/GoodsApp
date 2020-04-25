package com.ch.goods.app.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by CH on 2018/9/25.
 */

public class HttpRequestUtil {

    public static String URL = "http://121.199.56.252/api/productapi/";

    private OkHttpClient httpClient;
    private static HttpRequestUtil instance;

    public static HttpRequestUtil getInstance() {

        if (instance == null) {
            instance = new HttpRequestUtil();
        }
        return instance;
    }

    private HttpRequestUtil() {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        httpClient = okHttpClientBuilder
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * get 请求
     *
     * @param context 发起请求的context
     * @param url     url
     * @param params  参数
     */

    public void get(Context context, String baseUrl, String url, Map<String, Object> params, RequestCallback callback) {

        if (!IsNetConnect(context)) {
            Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //拼装接口
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //设置数据解析器
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)
                .build();
        // 创建 网络请求接口 的实例
        HttpRequest request = retrofit.create(HttpRequest.class);
        //创建请求，传入参数
        Call<String> call = request.httpGetRequest(url, params);
        call.enqueue(new MyCallBack(callback));

    }

    /**
     * get 请求
     *
     * @param context 发起请求的context
     * @param url     url
     */

    public void getByNoParams(Context context, String baseUrl, String url, RequestCallback callback) {

        if (!IsNetConnect(context)) {
            Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //拼装接口
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //设置数据解析器
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)
                .build();
        // 创建 网络请求接口 的实例
        HttpRequest request = retrofit.create(HttpRequest.class);
        //创建请求，传入参数
        Call<String> call = request.httpGetRequestByNoParams(url);
        call.enqueue(new MyCallBack(callback));

    }

    public void post(Context context, String baseUrl, String url, Map<String, String> params, RequestCallback callback) {

        if (!IsNetConnect(context)) {
            Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        //拼装接口
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //设置数据解析器
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)
                .build();
        // 创建 网络请求接口 的实例
        HttpRequest request = retrofit.create(HttpRequest.class);
        //创建请求，传入参数
        Call<String> call = request.httpPostRequest(url, params);
        call.enqueue(new MyCallBack(callback));

    }


    private class MyCallBack implements Callback<String> {

        private RequestCallback requestCallback;

        public MyCallBack(RequestCallback callback) {
            requestCallback = callback;
            requestCallback.onStart();
        }

        @Override
        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
            int responseCode = response.code();
            if (responseCode == 404 || responseCode >= 500) {
                requestCallback.onErro(response.message());
                requestCallback.onFinish();
                return;
            }
            JSONObject result = JSONObject.parseObject(response.body());
            String state = result.getString("state");
            String msg = result.getString("description");
            String data = result.getString("data");
            requestCallback.onSuccess(result, state, msg, data);
            requestCallback.onFinish();
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            requestCallback.onErro(t.getMessage());
            requestCallback.onFinish();
        }
    }


    private class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.e("requestUrl==========>", "请求的url:" + request.url());
            Request.Builder requestBuilder = null;
            try {
                requestBuilder = request.newBuilder()
                        .header("Content-Type", "application/json")
                        .method(request.method(), request.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
            Request request2 = requestBuilder.build();
            return chain.proceed(request2);
        }
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

    /***
     * 判断网络是否连接
     */
    public static boolean IsNetConnect(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

}
