package com.example.fangdou.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.ScrollingTabContainerView;

import com.example.fangdou.activity.QTabView;
import com.example.fangdou.activity.TabAdapter;


public class MyTabAdapter implements TabAdapter
{

    private String[] strings;
    private Context context;

    public MyTabAdapter(String[] strings, Context context)
    {
        this.strings = strings;
        this.context = context;
        System.out.println(context);
    }

    @Override
    public int getCount()
    {
        return strings.length;
    }

    @Override
    public int getBadge(int position)
    {
        return 0;
    }

    @Override
    public QTabView.TabIcon getIcon(int position)
    {
        return null;
    }

    @Override
    public QTabView.TabTitle getTitle(int position)
    {
        return new QTabView.TabTitle.Builder(context)
                .setContent(strings[position])
                .setTextColor(Color.BLUE, Color.BLACK)
                .build();
    }

    @Override
    public int getBackground(int position)
    {
        return 0;
    }
}
