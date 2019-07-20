package com.kangYh.fangdou2.app;

import android.os.Bundle;

import com.kangYh.fangdou2.MyApplication;
import com.kangYh.fangdou2.R;
import com.kangYh.fangdou2.app.discover.DiscoverFragment;
import com.kangYh.fangdou2.app.home.HomeFragment;
import com.kangYh.fangdou2.app.nearby.NearbyFragment;
import com.kangYh.fangdou2.base.BaseActivity;
import com.kangYh.fangdou2.base.BaseFragment;
import com.kangYh.fangdou2.di.components.DaggerActivityComponent;
import com.kangYh.fangdou2.di.modules.ActivityModule;
import com.kangYh.fangdou2.widget.bottomtab.CustomBottomTabWidget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MainActivity extends BaseActivity
{

    @BindView(R.id.tabWidget)
    CustomBottomTabWidget tabWidget;
    private List<BaseFragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);

        //初始化
        init();
        SQLiteStudioService.instance().start(this);
    }

    private void init() {
        //构造Fragment的集合
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new NearbyFragment());
        fragmentList.add(new DiscoverFragment());
      /*  fragmentList.add(new OrderFragment());
        fragmentList.add(new MineFragment());*/
        //初始化CustomBottomTabWidget
        tabWidget.init(getSupportFragmentManager(), fragmentList);
    }

    @Override
    protected void onDestroy()
    {
        SQLiteStudioService.instance().stop();
        super.onDestroy();
    }
}
