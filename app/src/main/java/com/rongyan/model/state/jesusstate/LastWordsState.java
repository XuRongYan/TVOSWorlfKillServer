package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.message.ToastMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/13.
 */

public class LastWordsState implements BaseJesusState {
    private static final String TAG = "LastWordsState";
    private int id = -1;
    @Override
    public void send(int... id) {
        Log.e(TAG, "遗言阶段");
        EventBus.getDefault().post(new ToastMessage("遗言阶段"));
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.SPEECH, this.id));
    }

    @Override
    public BaseJesusState next() {
        return null;
    }

    public LastWordsState(int id) {
        this.id = id;
    }

    public LastWordsState() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
