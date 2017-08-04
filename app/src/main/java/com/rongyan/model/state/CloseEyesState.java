package com.rongyan.model.state;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.entity.UserEntity;

/**
 * 闭眼状态
 * Created by XRY on 2017/7/28.
 */

public class CloseEyesState implements BaseState {
    private static final String TAG = "CloseEyesState";
    @Override
    public void send(UserEntity userEntity, int targetId) {
        Log.e(TAG, "闭眼");
    }
}
