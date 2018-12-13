package com.example.qq1296821114.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.example.qq1296821114.Data.Bean.Letter;
import com.example.qq1296821114.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.richeditor.RichEditor;

public class ReadActivity extends BaseActivity {

    @BindView(R.id.read_rich)
    RichEditor mEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._read_layout);
        ButterKnife.bind(this);

        init_view();
        init_data();
    }

    private void init_data() {
        Intent intent=getIntent();
        Letter letter= (Letter) intent.getSerializableExtra("letter");
        mEditor.setHtml(letter.getHtml());
    }

    private void init_view()
    {
        mEditor.setEditorHeight(200);
        //初始化字体大小
        mEditor.setEditorFontSize(22);
        //初始化字体颜色
        mEditor.setEditorFontColor(Color.BLACK);
        //mEditor.setEditorBackgroundColor(Color.BLUE);

        //初始化内边距
        mEditor.setPadding(10, 10, 10, 10);
        //设置编辑框背景，可以是网络图片
//        mEditor.setBackground("https://raw.githubusercontent.com/wasabeef/art/master/chip.jpg");
        // mEditor.setBackgroundColor(Color.BLUE);
        //设置默认显示语句
        mEditor.setPlaceholder("Insert text here...");
        //设置编辑器是否可用
        mEditor.setHtml("12458<br>5");
        mEditor.setInputEnabled(false);
    }
}
