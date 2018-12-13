package com.example.qq1296821114.Activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qq1296821114.Fragment.SendLetterFragment;
import com.example.qq1296821114.Fragment.SquareFragment;
import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.Adapter.FragmentAdapter;
import com.example.qq1296821114.View.view.MyViewPager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yalantis.guillotine.animation.GuillotineAnimation;

import java.util.List;

import com.yalantis.guillotine.animation.GuillotineAnimation;

import butterknife.ButterKnife;
import butterknife.BindView;

public class MActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private SystemBarTintManager tintManager;
    private NavigationView navigationView;
    ImageView menu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout._main_activity);

        init_view();
        //initWindow();
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        navigationView = (NavigationView) findViewById(R.id.nav);
        menu = (android.widget.ImageView) findViewById(R.id.main_menu);
        View headerView = navigationView.getHeaderView(0);//获取头布局
        menu.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //item.setChecked(true);
                Toast.makeText(MActivity.this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }


    private void init_view() {

        BottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.fl_container)
                .setTitleBeforeAndAfterColor("#bdbdbd", "#fafafa")
                .addItem(SquareFragment.class, "广场",
                        R.drawable.item1_before,
                        R.drawable.item1_after)
                .addItem(SendLetterFragment.class, "写信",
                        R.drawable.item2_before,
                        R.drawable.item2_after)
                .setIconHeight(25)
                .setIconWidth(27)
                .setTitleSize(14)
                .build();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu://点击菜单，跳出侧滑菜单
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
                break;
        }
    }

//    private void initWindow() {//初始化窗口属性，让状态栏和导航栏透明
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            tintManager = new SystemBarTintManager(this);
//            int statusColor = Color.parseColor("#1976d2");
//            tintManager.setStatusBarTintColor(statusColor);
//            tintManager.setStatusBarTintEnabled(true);
//        }
//    }
}