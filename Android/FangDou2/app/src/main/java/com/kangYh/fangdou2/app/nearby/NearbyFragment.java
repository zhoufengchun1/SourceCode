package com.kangYh.fangdou2.app.nearby;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kangYh.fangdou2.R;
import com.kangYh.fangdou2.base.BaseFragment;


/**
 * Created by solo on 2018/1/8.
 */

public class NearbyFragment extends BaseFragment
{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nearby, null);
        return view;

    }
}
