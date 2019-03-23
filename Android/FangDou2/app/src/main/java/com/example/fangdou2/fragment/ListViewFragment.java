package com.example.fangdou2.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fangdou2.R;
import com.example.fangdou2.adapter.MyAdapter;
import com.example.fangdou2.bean.ItemBean;

import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment implements MyAdapter.Callback
{
    View view;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
            {
                parent.removeView(view);
            }

        } else
        {
            view = inflater.inflate(R.layout.medialistview, null);
        }
        initView();
        return view;
    }

    public void initView()
    {
        List<ItemBean> itemBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            itemBeanList.add(new ItemBean("This is textString", R.raw.test));
        }
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter(itemBeanList, getLayoutInflater(), this));
    }

    @Override
    public void click(View v)
    {
        switch (v.getId())
        {
            case R.id.img_pause:
                Toast.makeText(v.getContext(), "pause", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
