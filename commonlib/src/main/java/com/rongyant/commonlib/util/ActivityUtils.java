package com.rongyant.commonlib.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by XRY on 2016/9/1.
 */
public class ActivityUtils {
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int fgmId) {
        if (fragmentManager == null || fragment == null) {
            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add(fgmId, fragment);

        transaction.commit();
    }

    public static void showFragment(@NonNull FragmentManager fragmentManager,
                                    @NonNull Fragment fragment){
        if (fragmentManager == null || fragment == null) {
            return;
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.show(fragment);

        transaction.commit();
    }

    public static void replaceFragment(FragmentManager manager, Fragment to, int fgmId) {
        FragmentTransaction transaction = manager.beginTransaction();
        //遍历隐藏所有添加的fragment
        for (Fragment fragment : manager.getFragments()) {
            transaction.hide(fragment);
        }
        if (!to.isAdded()) { //若没有添加过
            transaction.add(fgmId, to).commit();
        } else { //若已经添加
            transaction.show(to).commit();
        }

    }
}
