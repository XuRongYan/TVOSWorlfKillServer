package com.rongyan.model.state.jesusstate;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.message.ToastMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class TellerCloseEyesState implements BaseJesusState {
    private static final String TAG = "TellerCloseEyesState";
    @Override
    public void send(int...id) {
        Log.e(TAG, "预言家请闭眼");
        EventBus.getDefault().post(new ToastMessage("预言家请闭眼"));
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.CLOSE_EYES));
    }

    @Override
    public BaseJesusState next() {
        return new WitchOpenEyes();
    }
}
