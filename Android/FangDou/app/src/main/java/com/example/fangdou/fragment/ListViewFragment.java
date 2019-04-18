package com.example.fangdou.fragment;

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

import com.example.fangdou.MyDrawerLayout;
import com.example.fangdou.MyListView;
import com.example.fangdou.R;
import com.example.fangdou.Tts;
import com.example.fangdou.adapter.RecordAdapter;
import com.example.fangdou.bean.RecordItemBean;
import com.example.fangdou.utils.RecordingItem;

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
    private int nowPlayingId = 0, lastPlaying = 0;
    private TextView temp = null;



    public static Toolbar toolbar;

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

        toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("方逗");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_default));
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

        recordItemBeanList.add(new RecordItemBean("姑娘你就别跟我这逗闷子了，你心里还不跟个明镜儿似的", R.raw.media_beijing_1));
        recordItemBeanList.add(new RecordItemBean("海燕呐，你可长点心", R.raw.media_dongbei_1));
        recordItemBeanList.add(new RecordItemBean("你瞅你这个损色你瞅", R.raw.media_dongbei_2));
        recordItemBeanList.add(new RecordItemBean("妮儿，你别说话啦中不中", R.raw.media_henan_1));
        recordItemBeanList.add(new RecordItemBean("我好悔啊，我从一开始就不应该嫁过来，如果我不嫁过来，" +
                "我的夫君也不会死，如果我的夫君不死，我也不会沦落到这么一个伤心的地方", R.raw.media_shanxi_1));
        recordItemBeanList.add(new RecordItemBean("哥哥  又咋了嘛", R.raw.media_sichuan_1));
        recordItemBeanList.add(new RecordItemBean("晓得不", R.raw.media_sichuan_2));
        recordItemBeanList.add(new RecordItemBean("你啊晓得，我其实蛮喜欢你的，你啊可以做我男朋友", R.raw.media_suzhou_1));
        recordItemBeanList.add(new RecordItemBean("你是从哪冒出来的", R.raw.media_tangshan_1));
        recordItemBeanList.add(new RecordItemBean("对不住了您内，我是个警察", R.raw.media_tianjin_1));

        listView = view.findViewById(R.id.listView);
        listView.setAdapter(new RecordAdapter(recordItemBeanList, getLayoutInflater(), this));
        initSide();

    }

    @Override
    public void click(View v)
    {
        textView = v.findViewById(R.id.item_text);
        switch (v.getId())
        {
            case R.id.item_text:
                nowPlayingId = recordItemBeanList.get((Integer) v.getTag()).resourceId;
                //当前点击项就是当前播放（操作）项
                try
                {
                    if (!mediaPlayer.isPlaying())//如果没播放——有两种可能
                    {
                        if (lastPlaying != nowPlayingId)//当前项与上一次播放项不同
                        {
                            mediaPlayer.reset();//将上一个对象重置
                            mediaPlayer = MediaPlayer.create(view.getContext(), nowPlayingId);
                        }
                        mediaPlayer.start();
                        textView.setTextColor(getResources().getColor(R.color.color_lrcColor_Y));
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                        {
                            @Override
                            public void onCompletion(MediaPlayer mp)
                            {
                                textView.setTextColor(getResources().getColor(R.color.color_lrcColor_N));
                                Toast.makeText(view.getContext(), "播放完毕。", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else//播放也有两种可能
                    {
                        if (lastPlaying == nowPlayingId)//点击相同的
                        {
                            mediaPlayer.pause();//直接暂停
                            textView.setTextColor(getResources().getColor(R.color.color_lrcColor_N));
                        } else
                        {
                            mediaPlayer.reset();//将上一个对象重置
                            temp.setTextColor(getResources().getColor(R.color.color_lrcColor_N));
                            mediaPlayer = MediaPlayer.create(view.getContext(), nowPlayingId);
                            mediaPlayer.start();
                            textView.setTextColor(getResources().getColor(R.color.color_lrcColor_Y));
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                            {
                                @Override
                                public void onCompletion(MediaPlayer mp)
                                {
                                    textView.setTextColor(getResources().getColor(R.color.color_lrcColor_N));
                                    Toast.makeText(view.getContext(), "播放完毕。", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                } catch (NullPointerException e)
                {
                    mediaPlayer = MediaPlayer.create(view.getContext(), nowPlayingId);
                    mediaPlayer.start();
                    textView.setTextColor(getResources().getColor(R.color.color_lrcColor_Y));
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {
                        @Override
                        public void onCompletion(MediaPlayer mp)
                        {
                            textView.setTextColor(getResources().getColor(R.color.color_lrcColor_N));
                            Toast.makeText(view.getContext(), "播放完毕。", Toast.LENGTH_SHORT).show();
                        }
                    });
                } finally
                {
                    lastPlaying = nowPlayingId;
                    temp = textView;
                }
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




