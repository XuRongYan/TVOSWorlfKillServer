package com.rongyan.model.state.jesusstate;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/9/14.
 */

public class GiveChiefState implements BaseJesusState {
    private BaseJesusState nextState = null;
    private int deadId = -1;

    @Override
    public void send(int... id) {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.GIVE_CHIEF, id));
    }

    @Override
    public BaseJesusState next() {
        return nextState;
    }

    public void setNextState(BaseJesusState state) {
        nextState = state;
    }

    public void setDeadId(int deadId) {
        this.deadId = deadId;
    }

    public BaseJesusState getNextState() {
        return nextState;
    }

    public int getDeadId() {
        return deadId;
    }
}
