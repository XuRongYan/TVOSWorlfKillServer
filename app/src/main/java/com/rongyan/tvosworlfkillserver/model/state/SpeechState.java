package com.rongyan.tvosworlfkillserver.model.state;

import com.rongyan.tvosworlfkillserver.model.abstractinterface.BaseState;
import com.rongyan.tvosworlfkillserver.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEventEntity;
import com.rongyan.tvosworlfkillserver.model.enums.UserEventType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class SpeechState implements BaseState {
    /**
     * 停止发言
     * @param userEntity
     * @param targetId
     */
    @Override
    public void send(UserEntity userEntity, int targetId) {
        EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.END_SPEECH, -1));
    }
}
