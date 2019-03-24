package com.example.fangdou2.fragment;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fangdou2.R;
import com.example.fangdou2.Tts;
import com.example.fangdou2.adapter.MyAdapter;
import com.example.fangdou2.bean.ItemBean;
import com.example.fangdou2.utils.RecordingItem;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;


import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ListViewFragment extends Fragment implements MyAdapter.Callback
{
    private View view;
    private ListView listView;
    private MediaPlayer mediaPlayer;
    private TextView textView;
    private List<ItemBean> itemBeanList;
    private String app_id = "5c6e22da";


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
        StringBuffer param = new StringBuffer();
        param.append("appid=" + app_id);
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(view.getContext(), param.toString());

        initView();
        return view;
    }

    public void initView()
    {
        itemBeanList = new ArrayList<>();
        for (int i = 0; i < 20; i++)
        {
            if (i % 2 == 0)
            {
                itemBeanList.add(new ItemBean("This is textString", R.raw.test));
            } else
                itemBeanList.add(new ItemBean("哈哈哈哈哈哈哈", R.raw.test));
            //在此处添加文字与音频文件
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
                            mediaPlayer = MediaPlayer.create(view.getContext(), itemBeanList.get((Integer) v.getTag()).resourceId);
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
                new Tts(v, itemBeanList.get((Integer) v.getTag()).lrc);
            default:
                break;
        }
    }


}




