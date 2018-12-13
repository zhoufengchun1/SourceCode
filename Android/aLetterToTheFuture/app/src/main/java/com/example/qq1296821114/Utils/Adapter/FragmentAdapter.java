package com.example.qq1296821114.Utils.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.qq1296821114.Fragment.SendLetterFragment;
import com.example.qq1296821114.Fragment.SquareFragment;

public class FragmentAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;
    private SendLetterFragment sendLetterFragment;
    private SquareFragment squareFragment;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        sendLetterFragment = new SendLetterFragment();
        squareFragment = new SquareFragment();

    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = squareFragment;
                break;
            case 1:
                fragment = sendLetterFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
