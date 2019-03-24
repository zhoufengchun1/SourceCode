package com.example.fangdou2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

public class Tts
{
    // 语音合成对象
    private SpeechSynthesizer mTts;
    private Toast mToast;
    private View view;
    private int mPercentForBuffering = 0;
    // 播放进度
    private int mPercentForPlaying = 0;
    private String voicer = "xiaoyan";
    private String text;

    public Tts(View view, String text)
    {
        this.view = view;
        this.text = text;
        init();
    }

    public void init()
    {
        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(view.getContext(), mTtsInitListener);
        mToast = Toast.makeText(view.getContext(), "", Toast.LENGTH_LONG);
        setParamTTS();
        int code = mTts.startSpeaking(text, mTtsListener);
        if (code != ErrorCode.SUCCESS)
        {
            showTip("语音合成失败,错误码: " + code);
        }
    }

    /**
     * 语音合成初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener()
    {
        @Override
        public void onInit(int code)
        {
            Log.d("zhangdi", "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS)
            {
                showTip("初始化失败,错误码：" + code);
            }
            //else {
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
            //}
        }
    };

    private void setParamTTS()
    {

        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        //设置使用云端引擎,本地语音合成是收费的，所以忽略了
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
        //设置发音人
        mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
        //设置合成语速
        mTts.setParameter(SpeechConstant.SPEED, "50");
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        //设置合成音量
        mTts.setParameter(SpeechConstant.VOLUME, "50");
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");

        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

    }


    private void showTip(final String str)
    {
        mToast.setText(str);
        mToast.show();
    }

    private SynthesizerListener mTtsListener = new SynthesizerListener()
    {

        @Override
        public void onSpeakBegin()
        {
            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused()
        {
            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed()
        {
            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info)
        {
            // 合成进度
            mPercentForBuffering = percent;
            showTip(String.format(view.getContext().getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos)
        {
            // 播放进度
            mPercentForPlaying = percent;
            showTip(String.format(view.getContext().getString(R.string.tts_toast_format),
                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onCompleted(SpeechError error)
        {
            if (error == null)
            {
                showTip("播放完成");
            } else
            {
                showTip(error.getPlainDescription(true));
            }
        }


        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj)
        {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            // if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //    String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //    Log.d(TAG, "session id =" + sid);
            // }
        }
    };

}