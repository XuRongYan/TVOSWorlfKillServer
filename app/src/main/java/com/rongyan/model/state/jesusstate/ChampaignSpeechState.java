package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.message.ToastMessage;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/7.
 */

public class ChampaignSpeechState implements BaseJesusState {
    private static final String TAG = "ChampaignSpeechState";
    @Override
    public void send(int... id) {
        Log.e(TAG, "开始竞选发言");
        EventBus.getDefault().post(new ToastMessage("开始竞选发言"));
    }

    @Override
    public BaseJesusState next() {
        return null;
    }
}
