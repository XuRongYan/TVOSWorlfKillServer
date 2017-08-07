package com.rongyan.model.state.jesusstate;

import android.util.Log;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/7.
 */

public class ChampaignVoteState implements BaseJesusState {
    private static final String TAG = "ChampaignVoteState";
    @Override
    public void send(int... id) {
        Log.e(TAG, "开始进行竞选投票，已经上警的玩家不能投票");
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.CHIEF_VOTE, id));
    }

    @Override
    public BaseJesusState next() {
        return new NotifyState();
    }
}
