package com.rongyan.model.state.jesusstate;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;

/**
 * Created by XRY on 2017/7/28.
 */

public class SpeechState implements BaseJesusState {
    private static final String TAG = "SpeechState";
    @Override
    public void send(int...id) {
        Log.e(TAG, "玩家发言中...");
    }

    @Override
    public BaseJesusState next() {
        return new VottingState();
    }
}
