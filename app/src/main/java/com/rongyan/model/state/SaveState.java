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

public class SaveState implements BaseState {
    private static final String TAG = "SaveState";
    /**
     *
     * @param userEntity
     * @param targetId = 0不救，=1救
     */
    @Override
    public void send(UserEntity userEntity, int targetId) {
        if (targetId == -1) {
            Log.e(TAG, "不救");
            EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.NOT_SAVE, -1));
        } else {
            Log.e(TAG, "救");
            EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.SAVE, targetId));
        }
    }
}
