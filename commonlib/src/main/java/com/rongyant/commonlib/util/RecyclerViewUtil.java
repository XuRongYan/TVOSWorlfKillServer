package com.rongyant.commonlib.util;

import android.support.v7.widget.RecyclerView;

/**
 * Created by XRY on 2017/5/14.
 */

public class RecyclerViewUtil {
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
}
