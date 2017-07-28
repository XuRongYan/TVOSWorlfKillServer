package com.rongyan.tvosworlfkillserver.model.state;

import com.rongyan.tvosworlfkillserver.model.abstractinterface.BaseState;
import com.rongyan.tvosworlfkillserver.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEventEntity;
import com.rongyan.tvosworlfkillserver.model.enums.UserEventType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class SaveState implements BaseState {
    /**
     *
     * @param userEntity
     * @param targetId = 0不救，=1救
     */
    @Override
    public void send(UserEntity userEntity, int targetId) {
        if (targetId == 0) {
            EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.NOT_SAVE, -1));
        } else {
            EventBus.getDefault().post(new UserEventEntity(userEntity, UserEventType.SAVE, targetId));
        }
    }
}
