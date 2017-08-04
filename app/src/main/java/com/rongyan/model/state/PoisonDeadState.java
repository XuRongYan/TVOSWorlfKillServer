package com.rongyan.model.state;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.entity.UserEntity;

/**
 * Created by XRY on 2017/7/28.
 */

public class PoisonDeadState implements BaseState {
    private static final String TAG = "PoisonDeadState";
    @Override
    public void send(UserEntity userEntity, int targetId) {
        Log.e(TAG, "毒死");
    }
}
