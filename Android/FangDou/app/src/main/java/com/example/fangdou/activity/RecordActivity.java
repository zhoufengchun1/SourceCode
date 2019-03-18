package com.example.fangdou.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fangdou.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class RecordActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.video_view);
        jcVideoPlayerStandard.setUp("http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4", JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "我的视频");
        jcVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse("http://pic9.nipic.com/20100826/3320946_024307806453_2.jpg"));
    }

    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

}
