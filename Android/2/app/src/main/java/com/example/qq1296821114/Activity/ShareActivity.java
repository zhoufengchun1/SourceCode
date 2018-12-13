package com.example.qq1296821114.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.Util;

public class ShareActivity extends BaseActivity {

    private ImageView imageView;
    private Bitmap bitmap;
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/cache/pics";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._picture_dialog);
        imageView = findViewById(R.id.dialog_image);
        bitmap = Util.getBitmapFromLocal(FILE_PATH, "bitmap");
        imageView.setImageBitmap(bitmap);

    }
}
