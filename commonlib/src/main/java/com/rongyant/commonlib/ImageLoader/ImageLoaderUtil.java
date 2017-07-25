package com.rongyant.commonlib.ImageLoader;

import android.content.Context;

/**
 * 图片加载工具类
 * 单例模式
 * Created by XRY on 2016/8/31.
 */

public class ImageLoaderUtil {

    public static final int PIC_LARGE = 0;

    public static final int PIC_MEDIUM = 1;

    public static final int PIC_SMALL = 2;

    //普通加载策略
    public static final int LOAD_STRATEGY_NORMAL = 0;
    //wifi模式加载策略
    public static final int LOAD_STRATEGY_ONLY_WIFI = 1;

    private static ImageLoaderUtil mInstance;

    private BaseImageLoaderStrategy mStrategy;

    private ImageLoaderUtil() {
        mStrategy = new GlideImageLoaderStrategy();
    }

    //single instance
    public static ImageLoaderUtil getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtil.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtil();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }

    public void loadImage(Context context, ImageLoader img) {
        mStrategy.loadImage(context, img);
    }
}
