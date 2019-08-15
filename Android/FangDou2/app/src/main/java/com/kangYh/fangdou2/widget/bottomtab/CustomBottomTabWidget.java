package com.kangYh.fangdou2.widget.bottomtab;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.kangYh.fangdou2.R;
import com.kangYh.fangdou2.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import crossoverone.statuslib.StatusUtil;

/**
 * Created by solo on 2018/1/7.
 */

public class CustomBottomTabWidget extends LinearLayout
{

    private final View view;
    @BindView(R.id.ll_menu_home_page)
    LinearLayout llMenuHome;
    @BindView(R.id.ll_menu_nearby)
    LinearLayout llMenuNearby;
    @BindView(R.id.ll_menu_discover)
    LinearLayout llMenuDiscover;
    @BindView(R.id.vp_tab_widget)
    ViewPager viewPager;

    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragmentList;
    private TabPagerAdapter mAdapter;
    private Activity activity;

    public CustomBottomTabWidget(Context context)
    {
        this(context, null, 0);
    }

    public CustomBottomTabWidget(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomBottomTabWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        view = View.inflate(context, R.layout.widget_custom_bottom_tab, this);
        ButterKnife.bind(view);

        //设置默认的选中项
        selectTab(MenuTab.HOME);

    }

    /**
     * 外部调用初始化，传入必要的参数
     *
     * @param fm
     */
    public void init(FragmentManager fm, List<BaseFragment> fragmentList, Activity activity)
    {
        mFragmentManager = fm;
        this.activity = activity;
        mFragmentList = fragmentList;
        initViewPager();
    }

    /**
     * 初始化 ViewPager
     */
    private void initViewPager()
    {
        mAdapter = new TabPagerAdapter(mFragmentManager, mFragmentList);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                //将ViewPager与下面的tab关联起来
                switch (position)
                {
                    case 0:
                        selectTab(MenuTab.HOME);
                        StatusUtil.setSystemStatus(activity, true, false);
                        break;
                    case 1:
                        selectTab(MenuTab.NEARBY);
                        StatusUtil.setSystemStatus(activity, true, true);
                        break;
                    case 2:
                        selectTab(MenuTab.DISCOVER);
                        StatusUtil.setSystemStatus(activity, true, true);
                        break;
                    default:
                        selectTab(MenuTab.HOME);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
    }

    /**
     * 点击事件集合
     */
    @OnClick({R.id.ll_menu_home_page, R.id.ll_menu_nearby, R.id.ll_menu_discover})
    public void onViewClicked(View view)
    {

        switch (view.getId())
        {
            case R.id.ll_menu_home_page:
                selectTab(MenuTab.HOME);
                //使ViewPager跟随tab点击事件滑动
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_menu_nearby:
                selectTab(MenuTab.NEARBY);
                viewPager.setCurrentItem(1);
                break;
            case R.id.ll_menu_discover:
                selectTab(MenuTab.DISCOVER);
                viewPager.setCurrentItem(2);
                break;
        }
    }

    /**
     * 设置 Tab 的选中状态
     *
     * @param tab 要选中的标签
     */
    public void selectTab(MenuTab tab)
    {
        //先将所有tab取消选中，再单独设置要选中的tab
        unCheckedAll();

        switch (tab)
        {
            case HOME:
                llMenuHome.setActivated(true);
                break;
            case NEARBY:
                llMenuNearby.setActivated(true);
                break;
            case DISCOVER:
                llMenuDiscover.setActivated(true);
                break;
        }

    }


    //让所有tab都取消选中
    private void unCheckedAll()
    {
        llMenuHome.setActivated(false);
        llMenuNearby.setActivated(false);
        llMenuDiscover.setActivated(false);
    }

    /**
     * tab的枚举类型
     */
    public enum MenuTab
    {
        HOME,
        NEARBY,
        DISCOVER,
    }


}
