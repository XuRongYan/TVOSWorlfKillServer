package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.message.ToastMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/3.
 */

public class WatchCardState implements BaseJesusState {
    private static final String TAG = "WatchCardState";
    @Override
    public void send(int...id) {
        Log.e(TAG, "各位玩家请看牌");
        EventBus.getDefault().post(new ToastMessage("各位玩家请看牌"));
    }

    @Override
    public BaseJesusState next() {
        return new NightState();
    }
}
