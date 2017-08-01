package com.rongyan.model.state;


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
    @Override
    public void send(UserEntity userEntity, int targetId) {
        EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.GET, targetId));
    }
}
