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

public class ChiefCampaignState implements BaseJesusState {
    private static final String TAG = "ChiefCampaignState";
    @Override
    public void send(int...id) {
        Log.e(TAG, "开始进行警长竞选，想上警的玩家请举手");
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.CHIEF_CAMPAIGN));
    }

    @Override
    public BaseJesusState next() {
        return new ChampaignSpeechState();
    }
}
