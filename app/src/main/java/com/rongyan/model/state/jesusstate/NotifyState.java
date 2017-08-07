package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;

/**
 * Created by XRY on 2017/8/7.
 */

public class NotifyState implements BaseJesusState {
    private static final String TAG = "NotifyState";
    @Override
    public void send(int... id) {
        Log.e(TAG, "报夜");
    }

    @Override
    public BaseJesusState next() {
        return new SpeechState();
    }
}
