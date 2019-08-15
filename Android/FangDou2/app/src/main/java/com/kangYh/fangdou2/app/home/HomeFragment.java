package com.kangYh.fangdou2.app.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kangYh.fangdou2.MyApplication;
import com.kangYh.fangdou2.R;
import com.kangYh.fangdou2.app.home.activity.MainActivity;
import com.kangYh.fangdou2.app.home.activity.ManyActivity;
import com.kangYh.fangdou2.app.home.activity.VideoActivity;
import com.kangYh.fangdou2.app.home.adapter.LittleModuleAdapter;
import com.kangYh.fangdou2.app.home.adapter.VideoListAdapter;
import com.kangYh.fangdou2.app.home.model.IconTitleModel;
import com.kangYh.fangdou2.app.home.model.ImageTitleModel;
import com.kangYh.fangdou2.base.BaseFragment;
import com.kangYh.fangdou2.di.components.DaggerActivityComponent;
import com.kangYh.fangdou2.utils.GlideImageLoader;
import com.kangYh.fangdou2.widget.IconTitleView;
import com.kangYh.fangdou2.widget.decoration.HomeGridDecoration;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;

/**
 * Created by solo on 2018/1/8.
 */

public class HomeFragment extends BaseFragment implements HomeFragmentContract.View
{

    @BindView(R.id.home_banner)
    Banner banner;
    //大模块的LinearLayout布局
    @BindView(R.id.ll_big_module_fragment_home)
    LinearLayout llBigModule;
    //小模块GridView布局
    @BindView(R.id.recyclerview_little_module)
    RecyclerView littleModuleRecyclerView;
    //4块广告封装成的自定义View
    //下拉刷新组件
    @BindView(R.id.smartRefreshLayout_home)
    RefreshLayout smartRefreshLayout;

    @BindView(R.id.recyclerview_videolist)
    RecyclerView videoListRecyclerView;
    @Inject
    HomeFragmentContract.Presenter presenter;
    @BindView(R.id.home_root)
    LinearLayout homeRoot;
    private VideoListAdapter videoListAdapter;


    //private ShopListAdapter mShopListAdapter;
//    private List<ShopModel> mShopModels = Collections.EMPTY_LIST;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, null);
        //绑定 ButterKnife
        ButterKnife.bind(this, view);

        DaggerActivityComponent.builder()
                .applicationComponent(MyApplication.getApplicationComponent())
                .build()
                .inject(this);

        presenter.setContractView(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init()
    {
        initBanner();
        initLittleModuleRecyclerView();
        initVideoRecyclerView();
        initSmartRefreshLayout();

    }


    //初始化下拉刷新控件
    private void initSmartRefreshLayout()
    {
        smartRefreshLayout.setRefreshHeader(new BezierRadarHeader(getActivity()).setEnableHorizontalDrag(true));
        //设置 Footer 为 球脉冲 样式
        smartRefreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        smartRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        smartRefreshLayout.setEnableFooterFollowWhenLoadFinished(true);
        smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener()
        {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout)
            {
                videoListAdapter.addData(new ImageTitleModel(R.mipmap.homepage_icon_light_sichuanhua, "test2"));
                videoListAdapter.addData(new ImageTitleModel(R.mipmap.homepage_icon_light_sichuanhua, "test2"));
                videoListAdapter.addData(new ImageTitleModel(R.mipmap.homepage_icon_light_sichuanhua, "test2"));
                videoListAdapter.addData(new ImageTitleModel(R.mipmap.homepage_icon_light_sichuanhua, "test2"));
                videoListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore(true);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout)
            {
                Log.d("haha", "555");
                //模拟网络请求到的数据
                List<ImageTitleModel> datas = new ArrayList<>();
                datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_putonghua, "test1"));
                datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_sichuanhua, "test2"));
                datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_dongbeihua, "test3"));
                datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_guangdonghua, "test4"));
                datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_qita, "test5"));
                videoListAdapter.addData(datas);
                refreshlayout.finishLoadmore(2000/*,false*/);//不传时间则立即停止刷新    传入false表示加载失败
            }
        });

    }


    /**
     * 初始化小模块的RecyclerView
     */
    private void initLittleModuleRecyclerView()
    {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        //设置LayoutManager
        littleModuleRecyclerView.setLayoutManager(gridLayoutManager);
        //设置分割器
        littleModuleRecyclerView.addItemDecoration(new HomeGridDecoration(12));
        //设置动画
        littleModuleRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置Adapter
        List<IconTitleModel> iconTitleModels = presenter.getIconTitleModels();
        LittleModuleAdapter littleModuleAdapter = new LittleModuleAdapter(
                R.layout.view_icon_title_small, iconTitleModels);

        littleModuleRecyclerView.setAdapter(littleModuleAdapter);
        //设置item点击事件
        littleModuleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                Intent intent = new Intent(getActivity().getApplicationContext(), ManyActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initVideoRecyclerView()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        {
            @Override
            public boolean canScrollVertically()
            {
                return false;
            }
        };
        videoListRecyclerView.setLayoutManager(linearLayoutManager);
        videoListRecyclerView.addItemDecoration(new HomeGridDecoration(12));
        videoListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        List<ImageTitleModel> imageTitleModels = presenter.getImageTitleModels();
        videoListAdapter = new VideoListAdapter(R.layout.item_video_list, imageTitleModels);

        videoListRecyclerView.setAdapter(videoListAdapter);

        videoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position)
            {
                Intent intent = new Intent(getContext(), VideoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart()
    {
        super.onStart();
        //增加banner的体验
        banner.startAutoPlay();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        presenter.onStart();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        //增加banner的体验
        banner.stopAutoPlay();
    }

    private void initBanner()
    {
        //设置banner的各种属性
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader())
                .setImages(presenter.getBannerImages())
                .setBannerAnimation(Transformer.Default)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
    }

    /**
     * 往根布局上添加View
     */
    @Override
    public void addViewToBigModule(IconTitleView iconTitleView)
    {
        llBigModule.addView(iconTitleView);
    }

    @Override
    public void initVideoPlayer()
    {
       /* JzvdStd myJzvdStd = (JzvdStd) getActivity().findViewById(R.id.jz_video);
        myJzvdStd.setUp("http://jzvd.nathen.cn/342a5f7ef6124a4a8faf00e738b8bee4/cf6d9db0bd4d41f59d09ea0a81e918fd-5287d2089db37e62345123a1be272f8b.mp4"
                , "饺子快长大", JzvdStd.SCREEN_NORMAL);
        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png").into(myJzvdStd.thumbImageView);
        */
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        MainActivity.OnHideKeyboardListener onHideKeyboardListener = new MainActivity.OnHideKeyboardListener()
        {
            private InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            ;

            @Override
            public boolean hideKeyboard()
            {
                // TODO Auto-generated method stub
                if (inputMethodManager.isActive(getActivity().findViewById(R.id.activity_main_input_edittext)))
                {
                    getView().requestFocus();
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken()
                            , InputMethodManager.HIDE_NOT_ALWAYS);
                    return true;
                }
                return false;
            }
        };
        ((MainActivity) getActivity()).setOnHideKeyboardListener(onHideKeyboardListener);
        super.onAttach(context);

    }
}
