package com.example.changecolordemo;

public class Item
{
    private String name;
    private int imageId;
    public Item(String name,int imageId)
    {
        this.imageId=imageId;
        this.name=name;
    }
    public String getName()
    {
        return name;
    }
    public int getImageId()
    {
        return imageId;
    }
}