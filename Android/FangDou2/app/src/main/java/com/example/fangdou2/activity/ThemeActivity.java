package com.example.fangdou2.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.fangdou2.Item;
import com.example.fangdou2.R;
import com.example.fangdou2.adapter.ColorAdapter;
import com.example.fangdou2.fragment.ListViewFragment;
import com.example.fangdou2.fragment.MapFragment;
import com.example.fangdou2.utils.RippleAnimation;

import java.util.ArrayList;
import java.util.List;


public class ThemeActivity extends AppCompatActivity
{
    private List<Item> colorList = new ArrayList<Item>();

    public static int color = Color.parseColor("#22221e");
    public MainActivity mainActivity;

    public static void setStatusBarColor(Activity activity, int statusColor)
    {

        Window window = activity.getWindow();
        //取消状态栏透明
        //window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(statusColor);
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null)
        {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);

        }

    }

    private void initItem()
    {

        Item white = new Item("默认黑", R.drawable.white);
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        updateColor(getResources().getColor(R.color.color_default));
        setContentView(R.layout.activity_theme);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_default));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        initItem();
        final ColorAdapter adapter = new ColorAdapter(ThemeActivity.this, R.layout.color_item, colorList);
        final ListView listview = findViewById(R.id.choose_color);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Item item = colorList.get(position);
                onClick(item, view, toolbar, listview);
            }
        });

    }

    public void onClick(Item item, View view, Toolbar toolbar, ListView listView)
    {
        RippleAnimation.create(view).setDuration(800).start();
        switch (item.getName())
        {
            case "默认黑":
                color = getResources().getColor(R.color.color_default);
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
        //设置ThemeActivity的颜色
        setStatusBarColor(MainActivity.mapFragment.getActivity(), color);
        MapFragment.toolbar.setBackgroundColor(color);
        //设置地图的颜色
        ListViewFragment.toolbar.setBackgroundColor(color);
    }

    private void updateColor(int color)
    {
        setStatusBarColor(ThemeActivity.this, color);
    }

    private void initStatusBar()
    {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}
