package com.rongyant.commonlib.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by XRY on 2017/8/13.
 */

public class BitmapUtil {
    /**
     * 将bitmap转化为byte数组
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2Byte(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static Bitmap byte2Bitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
