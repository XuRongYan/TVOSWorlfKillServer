package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/10.
 */

public class HunterGetShootState implements BaseJesusState {
    private static final String TAG = "HunterGetShootState";
    @Override
    public void send(int... id) {
        Log.e(TAG, "猎人今晚的开枪状态是。。。");
        EventBus.getDefault().post(new JesusEventEntity(RoleType.HUNTER, JesusEvent.GET_SHOOT_STATE, id));
    }

    @Override
    public BaseJesusState next() {
        return new HunterCloseEyesState();
    }
}
