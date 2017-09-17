package com.rongyant.commonlib.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.rongyant.commonlib.util.LogUtils;

import java.util.Locale;

/**
 * Created by XRY on 2016/8/31.
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {
    @Override
    public void loadImage(Context context, ImageLoader img) {
        loadNormal(context, img);
    }

    private void loadNormal(Context context, ImageLoader img) {
        Glide.with(context)
                .load(img.getUrl())
                .placeholder(img.getPlaceHolder())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        LogUtils.d("Glide","onResourceReady", String.format(Locale.ROOT,
                                "onResourceReady(%s, %s, %s, %s, %s)", resource, model, target, isFromMemoryCache, isFirstResource));
                        return false;
                    }
                })
                .into(img.getImageView())
        ;
    }
}
