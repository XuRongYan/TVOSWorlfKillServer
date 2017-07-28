package com.rongyan.tvosworlfkillserver.model.state.jesusstate;

import com.rongyan.tvosworlfkillserver.model.abstractinterface.BaseJesusState;
import com.rongyan.tvosworlfkillserver.model.entity.JesusEventEntity;
import com.rongyan.tvosworlfkillserver.model.enums.JesusEvent;
import com.rongyan.tvosworlfkillserver.model.enums.RoleType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class TellerOpenEyesState implements BaseJesusState {
    @Override
    public void send(int id) {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.OPEN_EYES));
    }
}
