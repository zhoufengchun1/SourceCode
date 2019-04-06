package com.example.fangdou2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.fangdou2.bean.CityPath;
import com.example.fangdou2.bean.ViewAttr;
import com.example.fangdou2.calback.ParserCallBack;
import com.example.fangdou2.fragment.MapFragment;
import com.example.fangdou2.utils.SVGXmlParserUtils;

import java.util.List;

/**
 * Created by zhangjd on 2017/6/1.
 */

public class MapView extends View implements ParserCallBack
{
    public static List<CityPath> list;
    private ViewAttr mViewAttr;
    private Paint mPaint;
    private Path mPath;
    private float scaleX = 0.3f, scaleY = 0.3f;
    private int viewWidth;
    private int viewHeight;
    //是否计算完成
    private boolean isCalculation;
    private String string[];


    public MapView(Context context)
    {
        super(context);
        init();
    }

    public MapView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        setMeasuredDimension(getMeasureSize(300, widthMeasureSpec),
                getMeasureSize(300, heightMeasureSpec));
        System.out.println(widthMeasureSpec + "..." + heightMeasureSpec);
    }


    private int getMeasureSize(int defultSize, int measureSpce)
    {
        int mode = MeasureSpec.getMode(measureSpce);
        int size = MeasureSpec.getSize(measureSpce);
        int measureSize = defultSize;
        switch (mode)
        {
            case MeasureSpec.EXACTLY:
                measureSize = Math.max(defultSize, size);
                break;
            case MeasureSpec.AT_MOST:
                measureSize = defultSize;
                break;
        }
        return measureSize;
    }


    private void init()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        //解析svg xml
        string = new String[8];
        string = getResources().getStringArray(R.array.language_item_pinyin);
        SVGXmlParserUtils.parserXml(getResources().openRawResource(R.raw.pic_background_n), this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        myDraw();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (list == null)
        {
            return;
        }
        //        Matrix mMatrix = new Matrix();
//        mMatrix.postScale(0.5f,0.5f);
        //这个set方法不可以
//        mMatrix.setScale(0.5f,0.5f);
//        canvas.concat(mMatrix);
        //上面的方法也可以
//        canvas.restore();

        canvas.scale(scaleX, scaleY);
        //缩放
        canvas.drawColor(getResources().getColor(R.color.color_mapBackground));
        for (int i = 0; i < list.size(); i++)
        {
            CityPath path = list.get(i);
            //绘制边的颜色
            mPaint.setStrokeWidth(5);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.parseColor("#ffffff"));
            canvas.drawPath(path.getmPath(), mPaint);
        }
        if (mPath != null)
        {
            mPaint.setStrokeWidth(6);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(getResources().getColor(R.color.color_mapSelected));
            canvas.drawPath(mPath, mPaint);
        }

    }

    @Override
    public void callback(List<CityPath> list, ViewAttr mViewAttr)
    {
        MapView.list = list;
        this.mViewAttr = mViewAttr;
        myDraw();
    }

    private void myDraw()
    {
        if (!isCalculation && mViewAttr != null)
        {
            if (mViewAttr.getWidth() > 0 && mViewAttr.getHeight() > 0 && viewWidth > 0 && viewHeight > 0)
            {
                scaleX = scaleY = 0.85f;
            }
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x = event.getX();
            float y = event.getY();
            if (list != null)
                for (int i = 0; i < list.size(); i++)
                {
                    CityPath cityPath = list.get(i);
                    if (cityPath.isArea(x / scaleX, y / scaleY))
                    {
                        mPath = cityPath.getmPath();
                        postInvalidate();
                        MapFragment.listView.smoothScrollToPosition(initList(cityPath));
                        break;
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    public int initList(CityPath cityPath)
    {

        for (int i = 0; i < string.length; i++)
        {
            if (cityPath.getTitle().equals(string[i]))
            {
                return i;
            }
        }
        return 0;
    }

}
