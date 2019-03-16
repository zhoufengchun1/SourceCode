package com.example.fangdou;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.Transition;
import com.example.fangdou.activity.ListActivity;
import com.example.fangdou.adapter.*;
import com.example.fangdou.base.BaseRecAdapter;
import com.example.fangdou.base.BaseRecViewHolder;
import com.example.fangdou.widget.MyVideoPlayer;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class BlankFragment extends Fragment
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private RecyclerView recyclerView;
    private ArrayList<String> urlList;
    private ListVideoAdapter listVideoAdapter;

    private int firstVisibleItem;
    private int lastVisibleItem;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_square, container);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        urlList = new ArrayList<String>();

        urlList.add("");
        urlList.add("");
        urlList.add("");
        urlList.add("");
        urlList.add("");
        listVideoAdapter = new ListVideoAdapter(urlList);
        Log.d("hahaha", "123");
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(listVideoAdapter);
        addListener();
        return view;
    }


    private void addListener()
    {


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                switch (newState)
                {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        /**在这里执行，视频的自动播放与停止*/
                        autoPlayVideo(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        autoPlayVideo(recyclerView);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        JZVideoPlayer.releaseAllVideos();
                        break;
                }

            }
        });
    }

    /**
     * 自动播放
     */
    private void autoPlayVideo(RecyclerView recyclerView)
    {

        if (firstVisibleItem == 0 && lastVisibleItem == 0 && recyclerView.getChildAt(0) != null)
        {

            MyVideoPlayer videoView = null;
            if (recyclerView != null && recyclerView.getChildAt(0) != null)
            {
                videoView = recyclerView.getChildAt(0).findViewById(R.id.mp_video);
            }
            if (videoView != null)
            {
                if (videoView.currentState == JZVideoPlayer.CURRENT_STATE_NORMAL || videoView.currentState == JZVideoPlayer.CURRENT_STATE_PAUSE)
                {
                    videoView.startVideo();
                }
            }
        }

        for (int i = 0; i <= lastVisibleItem; i++)
        {
            if (recyclerView == null || recyclerView.getChildAt(i) == null)
            {
                return;
            }


            MyVideoPlayer
                    videoView = recyclerView.getChildAt(i).findViewById(R.id.mp_video);
            if (videoView != null)
            {

                Rect rect = new Rect();
                //获取视图本身的可见坐标，把值传入到rect对象中
                videoView.getLocalVisibleRect(rect);
                //获取视频的高度
                int videoHeight = videoView.getHeight();

                if (rect.top <= 100 && rect.bottom >= videoHeight)
                {
                    if (videoView.currentState == JZVideoPlayer.CURRENT_STATE_NORMAL || videoView.currentState == JZVideoPlayer.CURRENT_STATE_PAUSE)
                    {
                        videoView.startVideo();
                    }
                    return;
                }

                JZVideoPlayer.releaseAllVideos();

            } else
            {
                JZVideoPlayer.releaseAllVideos();
            }

        }

    }


    @Override
    public void onPause()
    {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }


    class ListVideoAdapter extends BaseRecAdapter<String, VideoViewHolder>
    {


        public ListVideoAdapter(List<String> list)
        {
            super(list);
        }

        @Override
        public void onHolder(VideoViewHolder holder, String bean, int position)
        {
            holder.mp_video.setUp(bean, JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
            if (position == 0)
            {
                holder.mp_video.startVideo();
                holder.tv_title.setVisibility(View.GONE);
            }
            Glide.with(context).load(bean).into(holder.mp_video.thumbImageView);
            holder.tv_title.setText("第" + position + "个视频");
        }

        @Override
        public VideoViewHolder onCreateHolder()
        {
            return new VideoViewHolder(getViewByRes(R.layout.item_video));

        }


    }

    public class VideoViewHolder extends BaseRecViewHolder
    {
        public View rootView;
        public MyVideoPlayer mp_video;
        public TextView tv_title;

        public VideoViewHolder(View rootView)
        {
            super(rootView);
            this.rootView = rootView;
            this.mp_video = rootView.findViewById(R.id.mp_video);
            this.tv_title = rootView.findViewById(R.id.tv_title);
        }

    }



}
