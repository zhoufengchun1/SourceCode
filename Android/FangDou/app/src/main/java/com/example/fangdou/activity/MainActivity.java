package com.example.fangdou.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.example.fangdou.R;
import com.example.fangdou.adapter.VideoAdapter;
import com.example.fangdou.fragment.Knowledge_fragment;
import com.example.fangdou.fragment.SquareFragment;
import com.example.fangdou.fragment.NavigationFragment;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MainActivity extends AppCompatActivity
{
    private ListView listView;
    private ArrayList<String> datas;
    private JCVideoPlayerStandard currPlayer;
    private VideoAdapter adapter;
    private AbsListView.OnScrollListener onScrollListener;
    private int firstVisible;//当前第一个可见的item
    private int visibleCount;//当前可见的item个数

    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmersionBar.with(this).statusBarColor(R.color.color_ActionBar).init();
        addFragment(new NavigationFragment(),R.id.navigation_fragment);
        addFragment(new SquareFragment(),R.id.square_fragment);
    }

    private void addFragment(Fragment fragment,int id)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);

        fragmentTransaction.commit();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }
}