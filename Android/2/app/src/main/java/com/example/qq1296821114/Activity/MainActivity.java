package com.example.qq1296821114.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.Util;
import com.example.qq1296821114.View.Dialog.ChooseColorDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.choose)
    Button choose;
    @BindView(R.id.md_900)
    Button md900;
    @BindView(R.id.md_800)
    Button md800;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Util.getPermission(this);
        activity = this;
        md800.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(activity, MActivity.class);
                startActivity(intent);
            }
        });
        md900.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.checkPermission()) {
                    Intent intent = new Intent();
                    intent.setClass(activity, Mp4MuxerActivity.class);
                    startActivity(intent);
                } else {
                    Util.makeToast(activity, "抱歉，我们需要一些权限才能继续运行", Toast.LENGTH_SHORT);
                    Util.getPermission(activity);
                }
            }
        });
    }

    @OnClick(R.id.choose)
    public void onViewClicked() {
        ChooseColorDialog chooseColorDialog = new ChooseColorDialog(this, getHandler());
        chooseColorDialog.show();
    }


}
