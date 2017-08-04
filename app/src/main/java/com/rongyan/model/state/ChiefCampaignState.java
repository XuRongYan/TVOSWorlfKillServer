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

public class ChiefCampaignState implements BaseState {
    private static final String TAG = "UserChiefCampaignState";
    @Override
    public void send(UserEntity userEntity, int targetId) {
        if (targetId == 0) {
            Log.e(TAG, userEntity.getUserId() + "号玩家不上警");
            EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.NOT_CHIEF_CAMPAIGN, -1));
        } else {
            Log.e(TAG, userEntity.getUserId() + "号玩家上警");
            EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.CHIEF_CAMPAIGN, -1));
        }
    }
}
