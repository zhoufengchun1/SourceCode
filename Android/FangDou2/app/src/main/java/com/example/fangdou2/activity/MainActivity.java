package com.example.fangdou2.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fangdou2.R;
import com.example.fangdou2.fragment.ListViewFragment;
import com.example.fangdou2.fragment.MapFragment;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements Serializable
{

    @BindView(R.id.MainView)
    LinearLayout MainView;
    @BindView(R.id.fl_fragment)
    FrameLayout flFragment;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.ll_tab1)
    LinearLayout llTab1;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.ll_tab2)
    LinearLayout llTab2;
    @BindView(R.id.ll)
    LinearLayout ll;
    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
    private String tips = "";
    private boolean flag = true;
    private AlertDialog dialog;
    private BottomNavigationView bottomNavigationView;
    private int lastfragment;
    private Fragment fragment[];

    private String app_id = "5c6e22da";
    public static MapFragment mapFragment;
    public static ListViewFragment listFragment;

    private List<TextView> tv_list;
    private Unbinder unbinder;
    private Fragment fragment_now = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        unbinder = ButterKnife.bind(this);
        initBottom();
        StringBuffer param = new StringBuffer();

        param.append("appid=" + app_id);
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(this, param.toString());
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
        initFragment();
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
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


    private void initFragment()
    {

        mapFragment = new MapFragment();
        listFragment = new ListViewFragment();
        fragment = new Fragment[]{mapFragment, listFragment};
        //这个方法很蠢，因为要更换主题，所以必须得先创建两个碎片的view。
        //以后更新考虑下函数回调
    }


    public void initBottom()
    {
        tv_list = new ArrayList<>();
        tv_list.add(tv1);
        tv_list.add(tv2);
        System.out.println(tv_list);
        changePageSelect(0);
        changePageFragment(R.id.ll_tab1);
    }

    public void changePageSelect(int index)
    {
        for (int i = 0; i < tv_list.size(); i++)
        {
            if (index == i)
            {
                tv_list.get(i).setTextColor(Color.parseColor("#ece638"));
            } else
            {
                tv_list.get(i).setTextColor(Color.parseColor("#000000"));
            }
        }
    }

    public void changePageFragment(int id)
    {
        switch (id)
        {
            case R.id.ll_tab1:
            case R.id.tv1:
                if (mapFragment == null)
                {//减少new fragment,避免不必要的内存消耗
                    mapFragment = new MapFragment();
                }
                changePageSelect(0);
                switchFragment(fragment_now, mapFragment);
                tv1.setBackgroundResource(R.drawable.bottom_cornerbackground_y);
                tv1.setTextColor(Color.parseColor("#000000"));
                tv2.setBackgroundResource(R.drawable.bottom_cornerbackground_n);
                tv2.setTextColor(Color.parseColor("#ece638"));
                break;
            case R.id.ll_tab2:
            case R.id.tv2:
                if (listFragment == null)
                {
                    listFragment = new ListViewFragment();
                }
                changePageSelect(1);
                switchFragment(fragment_now, listFragment);
                tv1.setBackgroundResource(R.drawable.bottom_cornerbackground_n);
                tv1.setTextColor(Color.parseColor("#ece638"));
                tv2.setBackgroundResource(R.drawable.bottom_cornerbackground_y);
                tv2.setTextColor(Color.parseColor("#000000"));
                break;

        }
    }

    public void switchFragment(Fragment from, Fragment to)
    {
        if (to == null)
            return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!to.isAdded())
        {
            if (from == null)
            {
                transaction.add(R.id.fl_fragment, to).show(to).commit();
            } else
            {
                // 隐藏当前的fragment，add下一个fragment到Activity中并显示
                transaction.hide(from).add(R.id.fl_fragment, to).show(to).commitAllowingStateLoss();
            }
        } else
        {
            // 隐藏当前的fragment，显示下一个
            transaction.hide(from).show(to).commit();
        }
        fragment_now = to;

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        //解除绑定
        unbinder.unbind();
    }

    @OnClick({R.id.tv1, R.id.ll_tab1, R.id.tv2, R.id.ll_tab2})
    public void onViewClicked(View view)
    {
        changePageFragment(view.getId());
    }
}
