package com.example.fangdou.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public final class MyViewPagerAdapter extends FragmentPagerAdapter
{
    public MyViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public MyViewPagerAdapter(FragmentManager fm, Fragment[] mFragmentArrays, String[] mTabTitles)
    {
        super(fm);
        this.mFragmentArrays = mFragmentArrays;
        this.mTabTitles = mTabTitles;
    }

    private Fragment[] mFragmentArrays;
    private String[] mTabTitles;

    @Override
    public Fragment getItem(int position)
    {
        return mFragmentArrays[position];
    }


    @Override
    public int getCount()
    {
        return mFragmentArrays.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTabTitles[position];
    }
}