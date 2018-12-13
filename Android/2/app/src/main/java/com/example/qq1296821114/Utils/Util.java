package com.example.qq1296821114.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.qq1296821114.Activity.MainActivity;
import com.example.qq1296821114.Data.Bean.Letter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class Util {

    private static Toast toast = null;
    private static String[] permissions = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache/pics";

    public static void setBaseColor(Context context, int i, Handler handler) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("theme_id", i);
        editor.apply();
        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);
        Message message = new Message();
        message.what = 1;
        handler.sendMessage(message);
    }

    public static int getBaseColor(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt("theme_id", 0);
    }

    public static void makeToast(Context context, String text, int duration) {
        if (toast == null) {
            toast = Toast.makeText(context, text, duration);
        }
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }

    public static boolean checkPermission() {
        for (String permission : permissions) {
            if (!Nammu.checkPermission(permission)) {
                return false;
            }
        }
        return true;
    }

    public static void getPermission(Activity activity) {
        Nammu.init(activity);
        StringBuilder permissionName = new StringBuilder();
        for (String permission : permissions) {
            if (!Nammu.checkPermission(permission)) {
                permissionName.append(permission).append("%");
            }
        }
        if (permissionName.length() > 0)
            permissionName.deleteCharAt(permissionName.length() - 1);
        Nammu.askForPermission(activity, permissionName.toString().split("%"), new PermissionCallback() {
            @Override
            public void permissionGranted() {
            }

            @Override
            public void permissionRefused() {
            }
        });
    }


    public static HashMap<String, Bitmap> getBitmapList(final Letter letter, Context context, Boolean isCompress, Boolean isSend) {
        String html = letter.getHtml();
        final HashMap<String, Bitmap> hashMap = new HashMap<>();
        String reg = "<img src=.*?>";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(html);
        boolean result = matcher.find();
        while (result) {
            String tmp = matcher.group();
            String src = tmp.substring(tmp.indexOf("img src=\"") + 10, tmp.indexOf("alt") - 1);
            src = src.replaceAll("\"", "");
            result = matcher.find();
            if (isCompress)
                comPressPicture(context, letter, hashMap, src, result, isSend);
        }
        return hashMap;
    }


    private static void comPressPicture(Context context, final Letter letter, final HashMap<String, Bitmap> hashMap,
                                        final String src, final boolean result, final boolean isSend) {
        Luban.with(context).load(src).putGear(1).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(File file) {
                Bitmap bitmap = getBitmapFromLocal(file.getPath());
                hashMap.put(src, bitmap);
                if (!result && isSend) {
                    String url = "http://120.77.174.141/ServletDemo/MyServlet",
                            url2 = "http://192.168.1.111:8080/MyWeb/MyServlet";
                    HttpUtil.httpPost(url, letter.toString());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        }).launch();
    }

    /**
     * 得到bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }


    /**
     * 向本地SD卡写网络图片
     *
     * @param bitmap
     */
    public static void saveBitmapToLocal(String fileName, Bitmap bitmap, int flag) {
        try {
            // 创建文件流，指向该路径，文件名叫做fileName
            File file;
            if (flag == 1) {
                file = new File(fileName);
            } else {
                file = new File(FILE_PATH, fileName);
            }
            // file其实是图片，它的父级File是文件夹，判断一下文件夹是否存在，如果不存在，创建文件夹
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                // 文件夹不存在
                fileParent.mkdirs();// 创建文件夹
            }
            // 将图片保存到本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    new FileOutputStream(file));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从本地SD卡获取缓存的bitmap
     */
    public static Bitmap getBitmapFromLocal(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapFromLocal(String FILE_PATH, String fileName) {
        try {
            File file = new File(FILE_PATH, fileName);
            if (file.exists()) {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(
                        file));
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * bitmap转化成string
     *
     * @param bitmap
     * @return
     */
    public static String convertIconToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] appicon = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(appicon, Base64.DEFAULT);
    }

    /**
     * string转成bitmap
     *
     * @param st
     */
    public static Bitmap convertStringToIcon(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap =
                    BitmapFactory.decodeByteArray(bitmapArray, 0,
                            bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }


}

