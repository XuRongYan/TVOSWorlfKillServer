package com.rongyan.model.state.jesusstate;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class HunterShootState implements BaseJesusState {
    private static final String TAG = "HunterShootState";
    @Override
    public void send(int...id) {
        Log.e(TAG, "猎人翻牌，请选择你想要枪杀的玩家号码");
        EventBus.getDefault().post(new JesusEventEntity(RoleType.HUNTER, JesusEvent.SHOOT_STATE, id));
    }

    @Override
    public BaseJesusState next() {
        return null;
    }
}
