package com.kangYh.fangdou2.app.home.model;

public class ImageTitleModel
{
    private int iconResource;
    private String title;

    public ImageTitleModel(int iconResource, String title) {
        this.iconResource = iconResource;
        this.title = title;
    }

    @Override
    public String toString() {
        return "IconTitleModel{" +
                "iconResource=" + iconResource +
                ", title='" + title + '\'' +
                '}';
    }

    public int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
