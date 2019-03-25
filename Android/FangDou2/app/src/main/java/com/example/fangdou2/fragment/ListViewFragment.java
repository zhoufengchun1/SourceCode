package com.example.fangdou2.fragment;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fangdou2.MyDrawerLayout;
import com.example.fangdou2.R;
import com.example.fangdou2.Tts;
import com.example.fangdou2.adapter.RecordAdapter;
import com.example.fangdou2.bean.RecordItemBean;
import com.example.fangdou2.utils.RecordingItem;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ListViewFragment extends Fragment implements RecordAdapter.Callback
{
    private View view;
    private ListView listView;
    private MediaPlayer mediaPlayer;
    private TextView textView;
    private List<RecordItemBean> recordItemBeanList;
    private String app_id = "5c6e22da";
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
            view = inflater.inflate(R.layout.medialistview, null);
        }
        StringBuffer param = new StringBuffer();
        param.append("appid=" + app_id);
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(view.getContext(), param.toString());
        setHasOptionsMenu(true);

        initView();
        initSide();
        System.out.println("123");
        return view;
    }

    public void initSide()
    {
        drawerLayout = (MyDrawerLayout) view.findViewById(R.id.listView_Fragment);
        navigationView = (NavigationView) view.findViewById(R.id.nav);
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
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new RecordAdapter(recordItemBeanList, getLayoutInflater(), this));

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




