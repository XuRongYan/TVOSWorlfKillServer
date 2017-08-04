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

public class SpeechState implements BaseState {
    private static final String TAG = "SpeechState";
    /**
     * 停止发言
     * @param userEntity
     * @param targetId
     */
    @Override
    public void send(UserEntity userEntity, int targetId) {
        Log.e(TAG, userEntity.getUserId() + "号玩家开始发言");
        EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.END_SPEECH, -1));
    }
}
