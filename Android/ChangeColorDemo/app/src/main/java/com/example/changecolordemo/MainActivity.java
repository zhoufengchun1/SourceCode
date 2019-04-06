package com.example.changecolordemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Item> colorList = new ArrayList<Item>();

    private void initItem() {
        for (int i = 0; i < 4; i++) {
            Item white = new Item("默认白", R.drawable.white);
            colorList.add(white);
            Item green = new Item("酷安绿", R.drawable.green);
            colorList.add(green);
            Item red = new Item("激情红", R.drawable.red);
            colorList.add(red);
            Item pink = new Item("哔哩粉", R.drawable.pink);
            colorList.add(pink);
            Item ching = new Item("水鸭青", R.drawable.ching);
            colorList.add(ching);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initItem();
        final ColorAdapter adapter = new ColorAdapter(MainActivity.this, R.layout.color_item, colorList);
        final ListView listview = (ListView) findViewById(R.id.choose_color);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = colorList.get(position);
                onClick(item, view, toolbar, listview);
            }


        });
    }


    public void onClick(Item item, View view, Toolbar toolbar, ListView listView) {
        RippleAnimation.create(view).setDuration(800).start();
        int color = 0;

        switch (item.getName()) {
            case "默认白":
                color = Color.parseColor("#808080");
                break;
            case "酷安绿":
                color = Color.parseColor("#109D58");
                break;
            case "激情红":
                color = Color.parseColor("#DC4437");
                break;
            case "哔哩粉":
                color = Color.parseColor("#FA7298");
                break;
            case "水鸭青":
                color = Color.parseColor("#019788");
                break;

        }

        updateColor(color);
        toolbar.setBackgroundColor(color);
        listView.setBackgroundColor(color);
    }

    private void updateColor(int color) {
        setStatusBarColor(MainActivity.this, color);
    }

    private void initStatusBar() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }
        }
    }

    static void setStatusBarColor(Activity activity, int statusColor) {

        Window window = activity.getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);

        }

    }

}