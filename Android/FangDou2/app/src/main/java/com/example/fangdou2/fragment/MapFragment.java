package com.example.fangdou2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fangdou2.R;
import com.example.fangdou2.adapter.LanguageAdapter;
import com.example.fangdou2.bean.LanguageItemBean;
import com.example.fangdou2.widget.MenuViewItem;

import java.util.ArrayList;

public class MapFragment extends Fragment implements View.OnClickListener, LanguageAdapter.Callback
{
    private View view;
    private int menuViewItem[];
    private String language_item[];
    private ArrayList<LanguageItemBean> arrayList;
    private ListView listView;
    private int locate;

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
            view = inflater.inflate(R.layout.maplayout, null);
        }
        initView();
        return view;
    }

    public void initView()
    {
        initItem();
        arrayList = new ArrayList<>();
        for (int i = 0; i < language_item.length; i++)
        {
            arrayList.add(new LanguageItemBean(language_item[i]));
        }
        listView = (ListView) view.findViewById(R.id.language_listView);
        listView.setAdapter(new LanguageAdapter(arrayList, getLayoutInflater(), this));

    }

    public void initItem()
    {
        int i = 0;
        menuViewItem = new int[8];
        language_item = new String[8];
        menuViewItem[i++] = R.id.GanFangYan;
        view.findViewById(R.id.GanFangYan).setOnClickListener(this);
        menuViewItem[i++] = R.id.GuanHuaFangYan;
        view.findViewById(R.id.GuanHuaFangYan).setOnClickListener(this);
        menuViewItem[i++] = R.id.KeJiaFangYan;
        view.findViewById(R.id.KeJiaFangYan).setOnClickListener(this);
        menuViewItem[i++] = R.id.KongBai;
        view.findViewById(R.id.KongBai).setOnClickListener(this);
        menuViewItem[i++] = R.id.MinFangYan;
        view.findViewById(R.id.MinFangYan).setOnClickListener(this);
        menuViewItem[i++] = R.id.WuFangYan;
        view.findViewById(R.id.WuFangYan).setOnClickListener(this);
        menuViewItem[i++] = R.id.XiangFangYan;
        view.findViewById(R.id.XiangFangYan).setOnClickListener(this);
        menuViewItem[i] = R.id.YueFangYan;
        view.findViewById(R.id.YueFangYan).setOnClickListener(this);
        //获取布局中的menuViewItem放置于数组中，便于调用
        language_item = view.getResources().getStringArray(R.array.language_item);
        //为每个item获取文字
    }

    @Override
    public void onClick(View v)
    {
        for (int i = 0; i < menuViewItem.length; i++)
        {
            if (v.getId() == menuViewItem[i])
            {
                locate = i;
                break;
            }
        }
        listView.smoothScrollToPosition(locate);
        Toast.makeText(v.getContext(), menuViewItem[locate] + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void click(View v)
    {

    }
}
