package com.example.fangdou2.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.fangdou2.R;
import com.example.fangdou2.fragment.ListViewFragment;
import com.example.fangdou2.fragment.NavigationFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends AppCompatActivity
{
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private String dialogTips = "", settingTips = "", tips = "";
    private boolean flag = true;
    private AlertDialog dialog;
    private SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            for (int i = 0; i < permissions.length; i++)
            {
                if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED)
                {
                    flag = false;
                    if (i == 0)
                    {
                        tips += "存储权限 ";
                    } else if (i == 1)
                    {
                        tips += "录音权限";
                    }
                }
            }
            if (!flag)
            {
                showDialogTipUserRequestPermission();
            }
        }

        addFragment(new NavigationFragment(), R.id.navigation_fragment);
        addFragment(new ListViewFragment(), R.id.listView_Fragment);

    }

    private void showDialogTipUserRequestPermission()
    {

        new AlertDialog.Builder(this)
                .setTitle(tips + "不可用")
                .setMessage("方逗需要获取" + tips + "\n否则，您将无法正常使用方逗\n")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        startRequestPermission();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission()
    {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b)
                    {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        showDialogTipUserGoToAppSetting();
                    } else
                        finish();
                } else
                {
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSetting()
    {

        dialog = new AlertDialog.Builder(this)
                .setTitle(tips)
                .setMessage("请在-应用设置-权限-中，允许方逗使用" + tips + "来保证必要功能")
                .setPositiveButton("立即开启", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting()
    {
        Intent intent = new Intent();

        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);

        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123)
        {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED)
                {
                    // 提示用户应该去应用设置界面手动开启权限
                    showDialogTipUserGoToAppSetting();
                } else
                {
                    if (dialog != null && dialog.isShowing())
                    {
                        dialog.dismiss();
                    }
                    Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    private void addFragment(Fragment fragment, int id)
    {

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);

        fragmentTransaction.commit();

    }

    private void initWindow()
    {//初始化窗口属性，让状态栏和导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            tintManager = new SystemBarTintManager(this);
            int statusColor = Color.parseColor("#1976d2");
            tintManager.setStatusBarTintColor(statusColor);
            tintManager.setStatusBarTintEnabled(true);
        }
    }
}
