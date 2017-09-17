package com.rongyant.commonlib.imageloader;

import android.content.Context;

/**
 * 加载图片的接口，由子类GlideImageLoaderStrategy去实现这个方法
 * Created by XRY on 2016/8/31.
 */
public interface BaseImageLoaderStrategy {
    void loadImage(Context context, ImageLoader img);
}
