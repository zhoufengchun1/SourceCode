package com.example.fangdou.adapter;

import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fangdou.R;
import com.example.fangdou.bean.RecordItemBean;

import java.util.List;


public class RecordAdapter extends BaseAdapter implements View.OnClickListener
{
    private List<RecordItemBean> mList;
    private LayoutInflater layoutInflater;
    private Callback mCallback;

    public RecordAdapter(List<RecordItemBean> mList, LayoutInflater layoutInflater, Callback callback)
    {
        this.mList = mList;
        this.layoutInflater = layoutInflater;
        this.mCallback = callback;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.recorditem, null);
            viewHolder.img_record = (ImageView) convertView.findViewById(R.id.img_record);
            viewHolder.img_natural = (ImageView) convertView.findViewById(R.id.img_natural);
            viewHolder.img_play = (ImageView) convertView.findViewById(R.id.img_play);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final RecordItemBean bean = mList.get(position);
        viewHolder.textView.setText(bean.lrc);
        //设置监听器
        viewHolder.textView.setOnClickListener(this);
        viewHolder.img_record.setOnClickListener(this);
        viewHolder.img_play.setOnClickListener(this);
        viewHolder.img_natural.setOnClickListener(this);
        //将ViewHolder与控件绑定
        viewHolder.textView.setTag(position);
        viewHolder.img_natural.setTag(position);
        return convertView;
    }

    public interface Callback
    {
        public void click(View v);
    }

    @Override
    public void onClick(View v)
    {
        mCallback.click(v);
    }

    class ViewHolder
    {
        public ImageView img_record;
        public ImageView img_play;
        public ImageView img_natural;
        public TextView textView;
    }


}
