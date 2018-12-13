package com.example.qq1296821114.Utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final static OkHttpClient client = new OkHttpClient();
    private static String TAG = "HttpUtil";


    public static void httpPost(final String url, final String json) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    RequestBody body = RequestBody.create(JSON, json);
                    Request request = new Request.Builder().post(body).url(url).build();
                    Response response = client.newCall(request).execute();
                    Log.e(TAG, String.valueOf(response.isSuccessful()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void httpGetData(final String url, final Callback callback)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request=new Request.Builder().url(url).build();
                Call call=client.newCall(request);
                call.enqueue(callback);
            }
        }).start();
    }
}
