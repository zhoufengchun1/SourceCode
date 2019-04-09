package com.example.fangdou2.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.fangdou2.MyDrawerLayout;
import com.example.fangdou2.MyListView;
import com.example.fangdou2.R;
import com.example.fangdou2.activity.ThemeActivity;
import com.example.fangdou2.adapter.LanguageAdapter;
import com.example.fangdou2.bean.LanguageItemBean;

import java.util.ArrayList;

public class MapFragment extends Fragment implements LanguageAdapter.Callback
{
    private View view;
    private String language_item[];
    private ArrayList<LanguageItemBean> arrayList;
    public static MyListView listView;
    private int locate;
    private InfoFragment infoFragment;
    private MyDrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    public static Toolbar toolbar;

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
            view = inflater.inflate(R.layout.fragment_map, null);
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
        listView = view.findViewById(R.id.language_listView);
        listView.setAdapter(new LanguageAdapter(arrayList, getLayoutInflater(), this));
        initLeftNavi();
    }


    @Override
    public void click()
    {

        infoFragment = new InfoFragment("示例文字", "https://x1aolata.github.io/fangdouwangye/index.html");
        getFragmentManager().beginTransaction().setCustomAnimations(R.anim.translate_into, R.anim.translate_into)
                .replace(R.id.infoFragment, infoFragment).addToBackStack(null).commit();


    }

    private void initLeftNavi()
    {
        drawerLayout = view.findViewById(R.id.leftDraw);
        navigationView = view.findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                Toast.makeText(getContext(), menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        /*下面是toolbar的设置，包括动画*/
        toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("方逗");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_default));
        ThemeActivity.setStatusBarColor(getActivity(), getResources().getColor(R.color.color_default));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, 0, 0)
        {
            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.syncState();
        drawerLayout.setDrawerListener(mDrawerToggle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                String string = menuItem.getTitle().toString();
                Intent intent;
                switch (string)
                {
                    case "DIY语音合成":
                        intent = new Intent("android.intent.action.build");
                        startActivity(intent);
                        break;
                    case "我的收藏/点赞":
                        Toast.makeText(view.getContext(), "敬请期待！", Toast.LENGTH_LONG).show();
                        break;
                    case "主题":
                        intent = new Intent("android.intent.action.theme");
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }


        });
    }

}
