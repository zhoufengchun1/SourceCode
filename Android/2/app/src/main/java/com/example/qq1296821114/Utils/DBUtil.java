package com.example.qq1296821114.Utils;

import android.content.Context;
import android.util.Log;

import com.example.qq1296821114.Data.Bean.Letter;
import com.example.qq1296821114.Data.DataBase.MyDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DBUtil {


    public static List<Letter> getLetter(final MyDB myDB, final DBUtilListener dbUtilListener, final Context context) {
        List<Letter> letters = myDB.loadLetter();
        if (letters.isEmpty() && dbUtilListener != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String url = "http://192.168.1.111:8080/MyWeb/GetDataServlet", url2 = "http://120.77.174.141/ServletDemo/GetDataServlet";
                    HttpUtil.httpGetData(url2, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            if (dbUtilListener != null)
                                dbUtilListener.loadFailure();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            String[] strings = string.split("@@@");
                            for (String s : strings) {
                                if (!s.equals("")) {
                                    try {
                                        Letter letter = new Letter(new JSONObject(s),context);
                                        myDB.saveLetter(letter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if (dbUtilListener != null)
                                dbUtilListener.loadSuccess();
                        }
                    });
                }
            }).start();
        }
        if (dbUtilListener != null)
            dbUtilListener.loadSuccess();
        return letters;
    }


    public interface DBUtilListener {
        void loadSuccess();

        void loadFailure();
    }
}
