package com.example.fangdou2.calback;

import com.example.fangdou2.bean.CityPath;
import com.example.fangdou2.bean.ViewAttr;

import java.util.List;


/**
 * Created by zhangjd on 2017/6/1.
 */

public interface ParserCallBack
{
    public void callback(List<CityPath> list, ViewAttr mViewAttr);
}
