package com.kangYh.fangdou2.widget.bottomtab;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.kangYh.fangdou2.base.BaseFragment;

import java.util.List;

/**
 * Created by solo on 2018/1/8.
 */

public class TabPagerAdapter extends FragmentPagerAdapter
{

    private List<BaseFragment> fragmentList;
    private FragmentManager fm;

    public TabPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fm = fm;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
