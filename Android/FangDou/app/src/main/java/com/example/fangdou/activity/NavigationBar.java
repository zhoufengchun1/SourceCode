package com.example.fangdou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.fangdou.R;

public class NavigationBar extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener
{
    private RadioGroup radioGroup;
    private int page;
    private final int Btn_Square = 1;
    private final int Btn_KnowLedge = 2;
    private final int Btn_Record = 3;


    public NavigationBar(int page)
    {
        this.page = page;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);
        radioGroup = (RadioGroup) findViewById(R.id.tabs_rg);
        radioGroup.setOnCheckedChangeListener(this);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        switch (checkedId)
        {
            case R.id.square:
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                startActivity(intent);
                Toast.makeText(this, "123", Toast.LENGTH_LONG);
                break;
            case R.id.knowledge:
                break;
            case R.id.start_record:
                break;
            default:
                break;

        }

    }
}

