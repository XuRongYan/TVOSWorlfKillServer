package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;

/**
 * Created by XRY on 2017/8/8.
 */

public class GameEndState implements BaseJesusState {
    private static final String TAG = "GameEndState";
    @Override
    public void send(int... id) {
        Log.e(TAG, "游戏结束");

    }

    @Override
    public BaseJesusState next() {
        return null;
    }
}
