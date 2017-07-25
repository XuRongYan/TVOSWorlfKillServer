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
}
