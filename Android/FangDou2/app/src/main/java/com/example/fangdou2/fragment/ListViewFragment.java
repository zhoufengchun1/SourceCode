package com.example.fangdou2.fragment;

import android.app.Activity;
import android.media.MediaPlayer;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.fangdou2.R;
import com.example.fangdou2.adapter.MyAdapter;
import com.example.fangdou2.bean.ItemBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ListViewFragment extends Fragment implements MyAdapter.Callback
{
    View view;
    ListView listView;
    MediaPlayer mediaPlayer;
    TextView textView;

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
        textView = (TextView) v.findViewById(R.id.item_text);
        switch (v.getId())
        {
            case R.id.item_text:
                try
                {
                    if (mediaPlayer != null && mediaPlayer.isPlaying())
                    {
                        textView.setTextColor(getResources().getColor(R.color.color_lrcColor_N));
                        mediaPlayer.pause();
                    } else
                    {
                        if (mediaPlayer == null)
                        {
                            mediaPlayer = MediaPlayer.create(view.getContext(), R.raw.test);
                            mediaPlayer.prepare();
                        }
                        mediaPlayer.start();
                        textView.setTextColor(getResources().getColor(R.color.color_lrcColor_Y));
                    }


                } catch (IOException e)
                {
                    e.printStackTrace();
                } catch (IllegalStateException e)
                {
                    assert mediaPlayer != null;
                    //假设mediaPlayer不是null
                    mediaPlayer.start();
                    textView.setTextColor(getResources().getColor(R.color.color_lrcColor_Y));
                }
                /*
                这里有个bug，第一次点击文字的时候一定会抛出IllegalStateException，再次点击就不会抛出
                妥协的方法是在catch中再写一次start()和文字颜色处理。
                 */
                break;
            default:
                break;
        }
    }


}
