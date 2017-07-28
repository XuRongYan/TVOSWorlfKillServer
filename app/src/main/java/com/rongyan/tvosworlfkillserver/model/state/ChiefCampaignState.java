package com.rongyan.tvosworlfkillserver.model.state;

import com.rongyan.tvosworlfkillserver.model.abstractinterface.BaseState;
import com.rongyan.tvosworlfkillserver.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEventEntity;
import com.rongyan.tvosworlfkillserver.model.enums.UserEventType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class ChiefCampaignState implements BaseState {
    @Override
    public void send(UserEntity userEntity, int targetId) {
        if (targetId == 0) {
            EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.NOT_CHIEF_CAMPAIGN, -1));
        } else {
            EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.CHIEF_CAMPAIGN, -1));
        }
    }
}
