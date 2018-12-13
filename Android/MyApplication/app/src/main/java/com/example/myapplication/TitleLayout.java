package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.jar.Attributes;

public class TitleLayout extends LinearLayout
{
    public TitleLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button backButton = (Button) findViewById(R.id.button_back);
        Button nextButton = (Button) findViewById(R.id.button_next);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((Activity)getContext()).finish();
            }
        });
        nextButton.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getContext(), "You click the NEXT button",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
