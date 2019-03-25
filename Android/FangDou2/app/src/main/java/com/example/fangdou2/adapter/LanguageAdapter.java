package com.example.fangdou2.adapter;

import android.view.LayoutInflater;
import android.view.*;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fangdou2.R;
import com.example.fangdou2.bean.LanguageItemBean;

import java.util.List;


public class LanguageAdapter extends BaseAdapter implements View.OnClickListener
{
    private List<LanguageItemBean> mList;
    private LayoutInflater layoutInflater;
    private Callback mCallback;

    public LanguageAdapter(List<LanguageItemBean> mList, LayoutInflater layoutInflater, Callback callback)
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
            convertView = layoutInflater.inflate(R.layout.languageitem, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.languageItem_text);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final LanguageItemBean bean = mList.get(position);
        viewHolder.textView.setText(bean.itemContent);
        //设置监听器
        viewHolder.textView.setOnClickListener(this);
        //将ViewHolder与控件绑定
        viewHolder.textView.setTag(position);
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
        public TextView textView;
    }


}
