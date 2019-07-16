package com.example.fangdou;

import com.example.fangdou.base.BasePresenter;
import com.example.fangdou.model.IconTitleModel;
import com.example.fangdou.model.ShopModel;
import com.example.fangdou.widget.IconTitleView;
import com.scwang.smartrefresh.layout.api.RefreshFooter;

import java.util.List;

/**
 * Created by solo on 2018/1/10.
 */

public interface HomeFragmentContract
{

    interface View {

        void addViewToBigModule(IconTitleView iconTitleView);

        void setShopListData(List<ShopModel> shopModels);

        void finishLoadmore(boolean success);

        void finishLoadmoreWithNoMoreData();

        void resetNoMoreData();

        void finishRefresh(boolean success);

        void setRefreshFooter(RefreshFooter footer);

        void addData2RecyclerView(List<ShopModel> shopModels);




    }

    interface Presenter extends BasePresenter<View>
    {

        List<Integer> getBannerImages();

        List<IconTitleModel> getIconTitleModels();

        void onLoadMore();

        void onRefresh();
    }
}
