package com.kangYh.fangdou2.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kangYh.fangdou2.R;

public class ItemView extends LinearLayout
{

    private TextView text;
    private ImageView imageLeft;
    private ImageView bottomLine;


    public ItemView(Context context)
    {
        super(context);
        init(context);
    }

    public ItemView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        String title = typedArray.getString(R.styleable.ItemView_text);
        boolean isBottom = typedArray.getBoolean(R.styleable.ItemView_show_bottomline, true);
        if (isBottom)
        {
            bottomLine.setVisibility(VISIBLE);
        } else
        {
            bottomLine.setVisibility(INVISIBLE);
        }

        int iconId = typedArray.getResourceId(R.styleable.ItemView_image, 0);
        imageLeft.setImageResource(iconId);
        text.setText(title);
        typedArray.recycle();
    }

    public ItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }


    public void init(Context context)
    {
        View.inflate(context, R.layout.item_view, this);

        text = findViewById(R.id.text);
        imageLeft = findViewById(R.id.image_left);
        bottomLine = findViewById(R.id.line);

    }

}
