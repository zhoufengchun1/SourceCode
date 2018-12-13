package com.example.firststart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MyImage extends AppCompatActivity  {
    private ImageView imageView;
    public void show()
    {
        imageView=(ImageView)findViewById(R.id.guild);
        imageView.setVisibility(View.GONE);
    }

}
