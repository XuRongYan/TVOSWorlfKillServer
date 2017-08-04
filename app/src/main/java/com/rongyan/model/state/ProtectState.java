package com.rongyan.model.state;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.entity.UserEntity;
import com.rongyan.model.entity.UserEventEntity;
import com.rongyan.model.enums.UserEventType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class ProtectState implements BaseState {
    private static final String TAG = "ProtectState";
    @Override
    public void send(UserEntity userEntity, int targetId) {
        Log.e(TAG, "守人");
        EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.PROTECT, targetId));
    }
}
