package com.rongyant.commonlib.imageloader;

import android.widget.ImageView;

/**
 * 图片加载类的封装，Builder模式
 * Created by XRY on 2016/8/31.
 */
public class ImageLoader {
    private int type; //类型（大图、小图、中图）
    private String url; //需要解析的url
    private int placeHolder; //当没有成功加载时显示的占位图
    private ImageView imageView; //imageView实例

    public ImageLoader(Builder builder) {
        this.type = builder.type;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.imageView = builder.imageView;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(int placeHolder) {
        this.placeHolder = placeHolder;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public static class Builder {
        private int type; //类型（大图、小图、中图）
        private String url; //需要解析的url
        private int placeHolder; //当没有成功加载时显示的占位图
        private ImageView imageView; //imageView实例

        public Builder type(int type) {
            this.type = type;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder imgView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }
    }
}
