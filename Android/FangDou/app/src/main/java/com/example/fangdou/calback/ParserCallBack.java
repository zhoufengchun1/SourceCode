package com.example.fangdou.calback;

import com.example.fangdou.bean.CityPath;
import com.example.fangdou.bean.ViewAttr;

import java.util.List;


/**
 * Created by zhangjd on 2017/6/1.
 */

public interface ParserCallBack
{
    public void callback(List<CityPath> list, ViewAttr mViewAttr);
}
