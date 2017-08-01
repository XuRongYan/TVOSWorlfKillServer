package com.rongyan.model.state.jesusstate;


import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class TellerCloseEyesState implements BaseJesusState {
    @Override
    public void send(int id) {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.CLOSE_EYES));
    }
}
