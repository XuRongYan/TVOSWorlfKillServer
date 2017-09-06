package com.rongyan.model.state.jesusstate;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.message.ToastMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class SpeechState implements BaseJesusState {
    private static final String TAG = "SpeechState";
    @Override
    public void send(int...id) {
        Log.e(TAG, "玩家发言中...");
        EventBus.getDefault().post(new ToastMessage("玩家发言中..."));
    }

    @Override
    public BaseJesusState next() {
        return null;
    }
}
