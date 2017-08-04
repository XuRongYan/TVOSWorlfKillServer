package com.rongyan.model.state;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.entity.UserEntity;

/**
 * Created by XRY on 2017/7/28.
 */

public class OpenEyesState implements BaseState {
    private static final String TAG = "OpenEyesState";
    @Override
    public void send(UserEntity userEntity, int targetId) {
        Log.e(TAG, "睁眼");
    }
}
