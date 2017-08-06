package com.rongyan.model.state.jesusstate;


import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/28.
 */

public class DaytimeState implements BaseJesusState {
    private static final String TAG = "DaytimeState";
    @Override
    public void send(int...id) {
        Log.e(TAG, "天亮了");
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.OPEN_EYES));
    }

    @Override
    public BaseJesusState next() {
        return new ChiefCampaignState();
    }
}
