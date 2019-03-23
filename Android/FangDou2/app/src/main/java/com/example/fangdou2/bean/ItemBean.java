package com.example.fangdou2.bean;

public class ItemBean
{
    public String lrc;
    //内容文本
    public int resourceId;

    //音频资源id
    public ItemBean(String lrc, int resourceId)
    {
        this.lrc = lrc;
        this.resourceId = resourceId;
    }
}
