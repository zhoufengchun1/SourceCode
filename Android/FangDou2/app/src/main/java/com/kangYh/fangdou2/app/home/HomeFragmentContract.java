package com.kangYh.fangdou2.app.home;

import com.cachecats.domin.shop.model.ShopModel;


import com.kangYh.fangdou2.app.home.model.IconTitleModel;
import com.kangYh.fangdou2.app.home.model.ImageTitleModel;
import com.kangYh.fangdou2.base.BasePresenter;
import com.kangYh.fangdou2.widget.IconTitleView;
import com.scwang.smartrefresh.layout.api.RefreshFooter;

import java.util.List;

/**
 * Created by solo on 2018/1/10.
 */

public interface HomeFragmentContract {

    interface View {

        void addViewToBigModule(IconTitleView iconTitleView);

        void initVideoPlayer();
    }

    interface Presenter extends BasePresenter<View>
    {

        List<Integer> getBannerImages();

        List<IconTitleModel> getIconTitleModels();

        List<ImageTitleModel> getImageTitleModels();

//        void onLoadMore();

    }
}
