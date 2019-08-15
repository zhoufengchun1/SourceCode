package com.kangYh.fangdou2.app.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.kangYh.fangdou2.R;
import com.kangYh.fangdou2.app.home.BlurTransformation;
import com.kangYh.fangdou2.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MineFragment extends BaseFragment
{

    @BindView(R.id.h_back)
    ImageView hBack;
    @BindView(R.id.h_head)
    ImageView hHead;


    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;

    }


    public void init()
    {
        Glide.with(getContext()).load(R.drawable.head)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(getContext(), 25)))
                .into(hBack);

        Glide.with(getContext()).load(R.drawable.head)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(hHead);

    }
}
