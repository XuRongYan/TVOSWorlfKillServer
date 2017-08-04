package com.rongyan.model.state;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.entity.UserEntity;
import com.rongyan.model.entity.UserEventEntity;
import com.rongyan.model.enums.UserEventType;

import de.greenrobot.event.EventBus;

/**
 * 预言家验人
 * Created by XRY on 2017/7/28.
 */

public class GetState implements BaseState {
    private static final String TAG = "GetState";
    @Override
    public void send(UserEntity userEntity, int targetId) {
        Log.e(TAG, "预言家获得状态");
        EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.GET, targetId));
    }
}
