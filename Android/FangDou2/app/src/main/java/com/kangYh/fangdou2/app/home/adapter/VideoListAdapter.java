package com.kangYh.fangdou2.app.home.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kangYh.fangdou2.R;
import com.kangYh.fangdou2.app.home.model.ImageTitleModel;

import java.util.List;

public class VideoListAdapter extends BaseQuickAdapter<ImageTitleModel, BaseViewHolder>
{
    private List<ImageTitleModel> list;

    public VideoListAdapter(int layoutResId, @Nullable List<ImageTitleModel> data)
    {
        super(layoutResId, data);
        list = data;

    }

    @Override
    protected void convert(BaseViewHolder helper, ImageTitleModel item)
    {
        helper.setImageResource(R.id.image1, item.getIconResource());
        helper.setImageResource(R.id.image2, item.getIconResource());

        helper.setText(R.id.text1, item.getTitle());
        helper.setText(R.id.text2, item.getTitle());

    }


}
