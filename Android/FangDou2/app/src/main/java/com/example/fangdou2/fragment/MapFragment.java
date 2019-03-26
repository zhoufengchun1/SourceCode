package com.example.fangdou2.fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fangdou2.MapView;
import com.example.fangdou2.R;
import com.example.fangdou2.adapter.LanguageAdapter;
import com.example.fangdou2.bean.CityPath;
import com.example.fangdou2.bean.LanguageItemBean;
import com.example.fangdou2.widget.MenuViewItem;

import java.util.ArrayList;

public class MapFragment extends Fragment implements LanguageAdapter.Callback
{
    private View view;
    private String language_item[];
    private ArrayList<LanguageItemBean> arrayList;
    public static ListView listView;
    private int locate;
    private InfoFragment infoFragment;

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
        //initItem();
        language_item = new String[8];
        language_item = view.getResources().getStringArray(R.array.language_item);
        arrayList = new ArrayList<>();
        for (int i = 0; i < language_item.length; i++)
        {
            arrayList.add(new LanguageItemBean(language_item[i]));
        }
        listView = (ListView) view.findViewById(R.id.language_listView);
        listView.setAdapter(new LanguageAdapter(arrayList, getLayoutInflater(), this));
    }


    @Override
    public void click(View v)
    {
        infoFragment = new InfoFragment("示例文字", "http://www.baidu.com");
        System.out.println("213");
        getFragmentManager().beginTransaction().replace(R.id.infoFragment, infoFragment).addToBackStack(null).commit();

    }

    public void finishMyChild()
    {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(infoFragment);
        transaction.commit();
    }
}
