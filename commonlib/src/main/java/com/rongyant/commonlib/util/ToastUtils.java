package com.rongyant.commonlib.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by XRY on 2016/8/31.
 */
public class ToastUtils {
    public static void showShort(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
