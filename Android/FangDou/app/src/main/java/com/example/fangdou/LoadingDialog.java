package com.example.fangdou;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.wang.avi.AVLoadingIndicatorView;

public class LoadingDialog extends AlertDialog
{

    private static LoadingDialog loadingDialog;
    private AVLoadingIndicatorView avi;

    public LoadingDialog(Context context, int themeResId)
    {
        super(context, themeResId);
    }

    public static LoadingDialog getInstance(Context context)
    {
        loadingDialog = new LoadingDialog(context, R.style.TransparentDialog); //设置AlertDialog背景透明
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);
        avi = this.findViewById(R.id.avi);
    }

    @Override
    public void show()
    {
        super.show();
        avi.show();
    }

    @Override
    public void dismiss()
    {
        super.dismiss();
        avi.hide();
    }
}