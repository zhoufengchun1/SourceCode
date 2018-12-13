package com.example.qq1296821114.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.qq1296821114.R;
import com.example.qq1296821114.View.view.MyVideoView;

public class LoadActivity extends BaseActivity {

    private MyVideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._welcome_activity);
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        videoview = (MyVideoView) findViewById(R.id.my_video_view);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.media));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                start_main();
            }
        });

        videoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_main();
            }
        });
    }

    private void start_main() {
        finish();
        Intent intent = new Intent();
        intent.setClass(this, MActivity.class);
        startActivity(intent);
    }

}
