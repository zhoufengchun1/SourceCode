package com.example.qq1296821114.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.Util;

public abstract class BaseActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    finish();
                    break;
            }
        }
    };

    private static int[] color = {R.style.reds, R.style.pinks, R.style.purples, R.style.deep_purples,
            R.style.indigo, R.style.blue, R.style.light_blue, R.style.cyan,
            R.style.amber, R.style.orange, R.style.deep_orange, R.style.brown,
            R.style.teal, R.style.green, R.style.light_green, R.style.yellow};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init_view();
    }

    private void init_view() {
        int theme_Id = Util.getBaseColor(this);
        setTheme(color[theme_Id]);
    }

    public Handler getHandler() {
        return handler;
    }
}
