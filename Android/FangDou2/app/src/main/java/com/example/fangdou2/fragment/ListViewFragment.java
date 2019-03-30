package com.example.fangdou2.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fangdou2.MyDrawerLayout;
import com.example.fangdou2.MyListView;
import com.example.fangdou2.R;
import com.example.fangdou2.Tts;
import com.example.fangdou2.adapter.RecordAdapter;
import com.example.fangdou2.bean.RecordItemBean;
import com.example.fangdou2.utils.RecordingItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ListViewFragment extends Fragment implements RecordAdapter.Callback
{
    private View view;
    private MyListView listView;
    private MediaPlayer mediaPlayer;
    private TextView textView;
    private List<RecordItemBean> recordItemBeanList;
    private MyDrawerLayout draw;
    private RadioGroup group;
    private String[] mCloudVoicersEntries;
    private String[] mCloudVoicersValue;
    private MyDrawerLayout drawerLayout;
    private NavigationView navigationView;
    private LayoutInflater inflater;
    private Menu menu;
    private Tts tts;
    private String voicer = "xiaoyan";
    private ActionBarDrawerToggle mDrawerToggle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        this.inflater = inflater;
        if (view != null)
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
            {
                parent.removeView(view);
            }

        } else
        {
            view = inflater.inflate(R.layout.fragment_listview, null);
        }

        setHasOptionsMenu(true);

        initView();
        initSide();
        return view;
    }

    public void initSide()
    {
        drawerLayout = view.findViewById(R.id.listView_Fragment);
        navigationView = view.findViewById(R.id.nav);
        mCloudVoicersEntries = view.getResources().getStringArray(R.array.voicer_cloud_entries);
        mCloudVoicersValue = view.getResources().getStringArray(R.array.voicer_cloud_values);
        menu = navigationView.getMenu();
        for (int i = 0; i < mCloudVoicersEntries.length; i++)
        {
            menu.add(1, i, 0, mCloudVoicersEntries[i]);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                //recorditem.setChecked(true);
                voicer = mCloudVoicersValue[item.getItemId()];
                Toast.makeText(view.getContext(), "现在是" + mCloudVoicersEntries[item.getItemId()], Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("方逗");
        toolbar.setTitleTextColor(Color.WHITE);

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (drawerLayout.isDrawerOpen(GravityCompat.END))
                {
                    drawerLayout.closeDrawers();
                } else
                {
                    drawerLayout.openDrawer(Gravity.END);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu = navigationView.getMenu();
        inflater.inflate(R.menu.menu_language, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    public void initView()
    {
        recordItemBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            if (i % 2 == 0)
            {
                recordItemBeanList.add(new RecordItemBean("嘿嘿嘿嘿嘿嘿嘿", R.raw.test));
            } else
                recordItemBeanList.add(new RecordItemBean("哈哈哈哈哈哈哈", R.raw.test));
            //在此处添加文字与音频文件
        }
        listView = view.findViewById(R.id.listView);
        listView.setAdapter(new RecordAdapter(recordItemBeanList, getLayoutInflater(), this));

    }

    @Override
    public void click(View v)
    {
        textView = v.findViewById(R.id.item_text);
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
                            mediaPlayer = MediaPlayer.create(view.getContext(), recordItemBeanList.get((Integer) v.getTag()).resourceId);
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
            case R.id.img_record:
                final RecordAudioDialogFragment fragment = RecordAudioDialogFragment.newInstance();
                fragment.show(getFragmentManager(), RecordAudioDialogFragment.class.getSimpleName());
                fragment.setOnCancelListener(new RecordAudioDialogFragment.OnAudioCancelListener()
                {
                    @Override
                    public void onCancel()
                    {
                        fragment.dismiss();
                    }
                });
                break;
            case R.id.img_play:
                RecordingItem recordingItem = new RecordingItem();
                SharedPreferences sharePreferences = v.getContext().getSharedPreferences("sp_name_audio", MODE_PRIVATE);
                final String filePath = sharePreferences.getString("audio_path", "");
                long elpased = sharePreferences.getLong("elpased", 0);
                recordingItem.setFilePath(filePath);
                recordingItem.setLength((int) elpased);
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(recordingItem);
                fragmentPlay.show(getFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
                break;
            case R.id.img_natural:
                tts = new Tts(v, recordItemBeanList.get((Integer) v.getTag()).lrc, voicer);
                tts = null;
            default:
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}




