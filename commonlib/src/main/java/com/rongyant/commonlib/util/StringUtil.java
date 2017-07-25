package com.rongyant.commonlib.util;

import android.text.TextUtils;

/**
 * Created by XRY on 2017/7/23.
 */

public class StringUtil {
    public static boolean isNullOrEmpty(String s) {
        return s == null || TextUtils.isEmpty(s);
    }
}
