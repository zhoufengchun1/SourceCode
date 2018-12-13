package com.example.qq1296821114.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.example.qq1296821114.R;

public class Test_Dialog extends Dialog {

    private ImageView imageView;
    private Bitmap bitmap;
    public Test_Dialog(@NonNull Context context,Bitmap bitmap) {
        super(context);
        this.bitmap=bitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._picture_dialog);
        imageView=findViewById(R.id.dialog_image);
        imageView.setImageBitmap(bitmap);
    }
}
