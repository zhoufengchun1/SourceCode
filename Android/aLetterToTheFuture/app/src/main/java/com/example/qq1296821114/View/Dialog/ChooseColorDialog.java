package com.example.qq1296821114.View.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.Util;

import butterknife.BindView;

public class ChooseColorDialog extends Dialog implements View.OnClickListener{

    private Handler handler;
    @BindView(R.id.red)
    Button red;
    @BindView(R.id.pink)
    Button pink;
    @BindView(R.id.purples)
    Button purples;
    @BindView(R.id.deep_purples)
    Button deepPurples;
    @BindView(R.id.indigo)
    Button indigo;
    @BindView(R.id.blue)
    Button blue;
    @BindView(R.id.light_blue)
    Button lightBlue;
    @BindView(R.id.cyan)
    Button cyan;
    @BindView(R.id.amber)
    Button amber;
    @BindView(R.id.orange)
    Button orange;
    @BindView(R.id.deep_orange)
    Button deepOrange;
    @BindView(R.id.brown)
    Button brown;
    @BindView(R.id.teal)
    Button teal;
    @BindView(R.id.green)
    Button green;
    @BindView(R.id.light_green)
    Button lightGreen;
    @BindView(R.id.yellow)
    Button yellow;
//    String[] colorname={"red","pink","purples","deep_purples","deepPurples","indigo","blue","lightblue",
//            "cyan","amber","orange","deepOrange","brown","teal","green","lightGreen","yellow"};

    public ChooseColorDialog(@NonNull Context context,Handler handler) {
        super(context);
        this.handler=handler;

    }

    private void init_view()
    {
        Button[] color={red,pink,purples,deepPurples,indigo,blue,lightBlue,cyan,amber,orange,
                deepOrange,brown,teal,green,lightGreen,yellow};
        for (int i=0;i<16;i++)
        {
            color[i].setAlpha(0.8f);
            color[i].setOnClickListener(this);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._choose_color_dialog);
        butterknife.ButterKnife.bind(this);
        init_view();
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();
        Button[] color={red,pink,purples,deepPurples,indigo,blue,lightBlue,cyan,amber,orange,
                deepOrange,brown,teal,green,lightGreen,yellow};
        for(int i=0;i<16;i++){
            if(id==color[i].getId())
            {
                Util.setBaseColor(getContext(),i,handler);
                break;
            }
        }
        this.dismiss();
    }
}
