package com.rongyan.tvosworlfkillserver.model.state;

import com.rongyan.tvosworlfkillserver.model.abstractinterface.BaseState;
import com.rongyan.tvosworlfkillserver.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEventEntity;
import com.rongyan.tvosworlfkillserver.model.enums.UserEventType;

import de.greenrobot.event.EventBus;

/**
 * 狼人请杀人
 * Created by XRY on 2017/7/28.
 */

public class KillState implements BaseState {
    @Override
    public void send(UserEntity userEntity, int targetId) {
        EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.KILL, targetId));
    }
}