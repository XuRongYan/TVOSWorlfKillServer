package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.message.ToastMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/8.
 */

public class GameEndState implements BaseJesusState {
    private static final String TAG = "GameEndState";
    @Override
    public void send(int... id) {
        Log.e(TAG, "游戏结束");
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.GAME_OVER));
        EventBus.getDefault().post(new ToastMessage("游戏结束"));
    }

    @Override
    public BaseJesusState next() {
        return null;
    }
}
