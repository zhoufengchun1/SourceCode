package com.example.qq1296821114.Data.Bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.qq1296821114.Utils.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class Letter implements Serializable {

    private String head;
    private HashMap<String, Bitmap> hashMap = new HashMap<>();

    private String html = "";
    private Date date;
    private String email;
    private int id;

    public Letter() {
    }

    public Letter(String html) {
        id = 0;
        this.html = html;
        date = new Date();
        email = "1296821114@qq.com";
        head = html.substring(0, html.length() > 10 ? 10 : html.length());
    }

    public Letter(JSONObject jsonObject, Context context) {
        try {
            id = jsonObject.getInt("id");
            html = jsonObject.getString("html");
            date = new Date();
            email = jsonObject.getString("email");

            JSONObject jsonObject1 = jsonObject.getJSONObject("bitmap");
            Iterator<?> it = jsonObject1.keys();
            while (it.hasNext()) {//遍历JSONObject
                String key = (String) it.next().toString();
                Bitmap bitmap = Util.convertStringToIcon(jsonObject1.getString(key));
                hashMap.put(key, bitmap);
            }

            ii(context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getBitmap(JSONObject jsonObject) {
        try {
            JSONObject jsonObject1 = jsonObject.getJSONObject("bitmap");
            Iterator<?> it = jsonObject1.keys();
            while (it.hasNext()) {//遍历JSONObject
                String key = (String) it.next().toString();
                Bitmap bitmap = Util.convertStringToIcon(jsonObject1.getString(key));
                hashMap.put(key, bitmap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public void ii(Context context) {
        String save = context.getExternalFilesDir("picture").getPath();
        for (Map.Entry<String, Bitmap> entry : hashMap.entrySet()) {
            String src = entry.getKey();
            String re_src = save + src.substring(src.lastIndexOf("/"), src.length());
            Util.saveBitmapToLocal(re_src, entry.getValue(), 1);
            html = html.replaceAll('/' + src, re_src);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getPicture() {
        for (Map.Entry<String, Bitmap> entry : hashMap.entrySet()) {
            return entry.getValue();
        }
        return null;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public void sendData(Context context) {
        hashMap.putAll(Util.getBitmapList(this, context, true, true));
    }

    public void getBitmap(Context context, Boolean isCompress) {
        hashMap.putAll(Util.getBitmapList(this, context, isCompress, false));
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JSONObject getHashMap() {
        if (hashMap.isEmpty())
            return null;
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Bitmap> entry : hashMap.entrySet()) {
            try {
                jsonObject.put(entry.getKey(), Util.convertIconToString(entry.getValue()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    private JSONObject getJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("html", html);
            jsonObject.put("date", date.toString());
            jsonObject.put("email", email);
            JSONObject jsonObject1 = getHashMap();
            if (jsonObject1 == null)
                jsonObject.put("bitmap", null);
            else
                jsonObject.put("bitmap", jsonObject1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public String toString() {
        if (hashMap != null)
            Log.e("HashMap", String.valueOf(hashMap.size()));
        return getJSONObject().toString();
    }
}
