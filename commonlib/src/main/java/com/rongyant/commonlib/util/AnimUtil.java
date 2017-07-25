package com.rongyant.commonlib.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.rongyant.commonlib.R;


/**
 * 动画工具类
 * <p>存储应用中常用动画,调用其中方法执行动画</p>
 *
 * @author linxiao
 * @version 1.0
 */
public class AnimUtil {

    /**
     * 抖动动画
     * */
    public static void shakeView(Context context, View view) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }

    /**
     * 淡入动画
     * */
    public static void fadeInView(Context context, View view) {
        Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        view.startAnimation(fadeIn);
    }

    /**
     * 淡出动画
     * */
    public static void fadeOutView(Context context, View view) {
        Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        view.startAnimation(fadeOut);
    }

}
