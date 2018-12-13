package com.example.qq1296821114.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.qq1296821114.Fragment.SendLetterFragment;
import com.example.qq1296821114.Fragment.SquareFragment;
import com.example.qq1296821114.R;
import com.example.qq1296821114.Utils.Adapter.FragmentAdapter;
import com.example.qq1296821114.View.view.MyViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.List;

public class MActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id._main_drawer_layout)
    DrawerLayout MainDrawerLayout;
    @BindView(R.id.main_viewPager)
    MyViewPager mainViewPager;
    @BindView(R.id.square)
    Button square;
    @BindView(R.id.send_letter)
    Button sendLetter;

    private FragmentAdapter fragmentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._main_activity);
        //ButterKnife.bind(this);
        //init_view();

    }

    private void init_view() {
        mainViewPager.setScanScroll(false);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(fragmentAdapter);

        square.setOnClickListener(this);
        sendLetter.setOnClickListener(this);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 获取到Activity下的Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null) {
            return;
        }
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                // 这里就会调用我们Fragment中的onRequestPermissionsResult方法
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.square:
                mainViewPager.setCurrentItem(0);
                fragmentAdapter.notifyDataSetChanged();
                break;
            case R.id.send_letter:
                mainViewPager.setCurrentItem(1);
                fragmentAdapter.notifyDataSetChanged();
                break;
        }
    }


}
