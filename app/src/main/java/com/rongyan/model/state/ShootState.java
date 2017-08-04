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

public class ShootState implements BaseState {
    private static final String TAG = "ShootState";
    @Override
    public void send(UserEntity userEntity, int targetId) {
        Log.e(TAG, "开枪带走" + userEntity.getUserId());
        EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.SHOOT, targetId));
    }
}
