package com.example.firststart;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.firststart.MyImage;

public class GuildFragment extends Fragment {
    private MyView customVideoView;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        customVideoView = new MyView(getContext());
        /**获取参数，根据不同的参数播放不同的视频**/
        int index = getArguments().getInt("index");
        Uri uri;
        View view=this.getLayoutInflater().inflate(R.layout.guild_layout,null);
        imageView=view.findViewById(R.id.guild);
        imageView.setVisibility(View.GONE);
        if (index == 1) {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video_1);
            customVideoView.playVideo(uri);
        } else {
            uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.video_2);
            customVideoView.playVideo_2(uri);

        }
        /**播放视频**/

        return customVideoView;
    }

    /**
     * 记得在销毁的时候让播放的视频终止
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (customVideoView != null) {
            customVideoView.stopPlayback();
        }
    }


}