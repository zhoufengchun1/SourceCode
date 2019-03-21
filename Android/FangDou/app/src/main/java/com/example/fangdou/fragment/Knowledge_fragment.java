package com.example.fangdou.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fangdou.R;
import com.example.fangdou.activity.TabView;
import com.example.fangdou.activity.VerticalTabLayout;
import com.example.fangdou.adapter.MyTabAdapter;
import com.example.fangdou.adapter.MyViewPagerAdapter;


public class Knowledge_fragment extends Fragment
{

    private int num = 7;
    private TabLayout tabLayout;

    private ViewPager viewPager;

    private VerticalTabLayout verticalTabLayout;

    private Fragment[] mFragmentArrays = new Fragment[num];

    private String[] mTabTitles = new String[num];

    private String[] mLeftTabTitles = new String[20];

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        System.out.println(R.layout.tab_layout);
        view = inflater.inflate(R.layout.tab_layout, null, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.tab_viewpager);

        initView();
        initLeftTab();
        return view;
    }

    private void initView()
    {
        mTabTitles[0] = "四川话";
        mTabTitles[1] = "粤语";
        mTabTitles[2] = "唐山话";
        mTabTitles[3] = "普通话";
        mTabTitles[4] = "沧州话";
        mTabTitles[5] = "沧州话";
        mTabTitles[6] = "沧州话";

        for (int i = 0; i < 20; i++)
        {
            mLeftTabTitles[i] = "示例文本";
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        for (int i = 0; i < num; i++)
        {
            mFragmentArrays[i] = TabFragment.newInstance();
        }
        //PagerAdapter pagerAdapter = new MyViewPagerAdapter(getFragmentManager(),mTabTitles,new Fragment[num]);
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getFragmentManager(), mFragmentArrays, mTabTitles);

        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
    }


    public void initLeftTab()
    {
        verticalTabLayout = (VerticalTabLayout) view.findViewById(R.id.leftTab);
        verticalTabLayout.setTabAdapter(new MyTabAdapter(mLeftTabTitles,view.getContext()));
        verticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabView tab, int position)
            {

            }

            @Override
            public void onTabReselected(TabView tab, int position)
            {

            }
        });
        /*verticalTabLayout.addTab(new QTabView(view.getContext()));
        verticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {

            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });*/


    }


}


