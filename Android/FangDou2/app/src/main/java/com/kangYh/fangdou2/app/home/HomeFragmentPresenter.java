package com.kangYh.fangdou2.app.home;

import android.content.Context;
import android.widget.LinearLayout;

import com.cachecats.domin.shop.service.GroupPackageService;
import com.cachecats.domin.shop.service.ShopService;
import com.kangYh.fangdou2.R;
import com.kangYh.fangdou2.app.home.model.IconTitleModel;
import com.kangYh.fangdou2.app.home.model.ImageTitleModel;
import com.kangYh.fangdou2.mock.MockUtils;
import com.kangYh.fangdou2.utils.ToastUtils;
import com.kangYh.fangdou2.widget.IconTitleView;
import com.orhanobut.logger.Logger;
import com.solo.common.rxjava.CloseableRxServiceExecutor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by solo on 2018/1/10.
 */

public class HomeFragmentPresenter implements HomeFragmentContract.Presenter
{

    //大模块的图片数组
    private static final int[] bigModuleDrawables = {
            R.mipmap.homepage_icon_light_rankinglist,
            R.mipmap.homepage_icon_light_like,
    };

    //大模块的标题数组
    private static final String[] bigModuleTitles = {
            "排行榜", "猜你喜欢"
    };

    private HomeFragmentContract.View mFragment;
    private Context mContext;
    private ShopService shopService;
    private GroupPackageService groupPackageService;
    private MockUtils mockUtils;
    private CloseableRxServiceExecutor executor;
    private boolean flag = true;

    @Inject
    public HomeFragmentPresenter(Context context,
                                 ShopService shopService,
                                 GroupPackageService groupPackageService,
                                 MockUtils mockUtils,
                                 CloseableRxServiceExecutor executor)
    {
        mContext = context;
        this.shopService = shopService;
        this.groupPackageService = groupPackageService;
        this.mockUtils = mockUtils;
        this.executor = executor;
    }

    @Override
    public void setContractView(HomeFragmentContract.View fragment)
    {
        mFragment = fragment;
    }

    @Override
    public void onStart()
    {

        initBigModule();
        mockUtils.mockShopDataToDB();
        mockUtils.clearShop();
        mockUtils.mockGroupPackagesToDB();
//        getAllShops();
        mockUtils.mockGroupInfoData();
//        getFirstPageShops();
    }

    @Override
    public void onDestroy()
    {
    }


    /**
     * 初始化banner下面的5个大模块
     */
    private void initBigModule()
    {
        if (flag)
        {
            for (int i = 0; i < 2; i++)
            {
                IconTitleView iconTitleView = IconTitleView.newInstance(mContext, bigModuleDrawables[i], bigModuleTitles[i]);
                // 设置宽高和权重weight，使每个View占用相同的宽度
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                iconTitleView.setLayoutParams(lp);

                // 往根布局上添加View
                mFragment.addViewToBigModule(iconTitleView);

                //给View添加点击事件
                int finalI = i;
                iconTitleView.setOnClickListener((view) ->
                {
                    Logger.d(bigModuleTitles[finalI]);
                    ToastUtils.show(bigModuleTitles[finalI]);
                });
            }
            flag = false;
        }
    }

    /**
     * 获取Banner的图片资源
     *
     * @return
     */
    @Override
    public List<Integer> getBannerImages()
    {
        List<Integer> mBannerImages = new ArrayList<>();
        mBannerImages.add(R.mipmap.banner_1);
        mBannerImages.add(R.mipmap.banner_2);
        mBannerImages.add(R.mipmap.banner_3);
        mBannerImages.add(R.mipmap.banner_4);
        return mBannerImages;
    }

    /**
     * 获取包含两行小模块的图标、标题的对象的集合
     *
     * @return
     */
    @Override
    public List<IconTitleModel> getIconTitleModels()
    {
        List<IconTitleModel> datas = new ArrayList<>();
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_light_putonghua, "普通话"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_light_sichuanhua, "四川话"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_light_dongbeihua, "东北话"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_light_guangdonghua, "粤语"));
        datas.add(new IconTitleModel(R.mipmap.homepage_icon_light_qita, "全部种类"));

        return datas;
    }

    @Override
    public List<ImageTitleModel> getImageTitleModels()
    {
        List<ImageTitleModel> datas = new ArrayList<>();
        datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_putonghua, "test1"));
        datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_sichuanhua, "test2"));
        datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_dongbeihua, "test3"));
        datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_guangdonghua, "test4"));
        datas.add(new ImageTitleModel(R.mipmap.homepage_icon_light_qita, "test5"));

        return datas;
    }


}
