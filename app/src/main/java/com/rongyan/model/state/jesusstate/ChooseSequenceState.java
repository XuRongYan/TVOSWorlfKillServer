package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.message.ToastMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/11.
 */

public class ChooseSequenceState implements BaseJesusState {
    private static final String TAG = "ChooseSequenceState";
    @Override
    public void send(int... id) {
        Log.e(TAG, "请选择发言顺序");
        EventBus.getDefault().post(new ToastMessage("请选择发言顺序"));
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.CHOOSE_SEQUENCE, id));
    }

    @Override
    public BaseJesusState next() {
        return new SpeechState();
    }
}
