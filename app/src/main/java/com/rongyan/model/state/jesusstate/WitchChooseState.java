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

public class WitchChooseState implements BaseJesusState {
    private static final String TAG = "WitchChooseState";
    @Override
    public void send(int...id) {
        Log.e(TAG, "女巫请选择要杀人还是救人");
        EventBus.getDefault().post(new JesusEventEntity(RoleType.WITCH, JesusEvent.SAVE, id));
    }

    @Override
    public BaseJesusState next() {
        return new WitchCloseState();
    }
}
