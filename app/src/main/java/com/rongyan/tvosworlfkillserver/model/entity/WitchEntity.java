package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.model.GameManager;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.Role;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.WitchFunction;
import com.rongyan.tvosworlfkillserver.model.enums.WitchMedicine;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class WitchEntity extends Role implements WitchFunction {
    private int poisonNum = 1;
    private int liveNum = 1;
    public WitchEntity(Map<Integer, Role> players) {
        super(players);
        good = true;
    }

    @Override
    public void setManagerIndex() {
        if (id != -1) {
            GameManager.setWitchId(id);
        }
    }

    @Override
    public void poisonOrLive(WitchMedicine type, int number) {
        if (type == WitchMedicine.POISON && poisonNum != 0) {
            players.get(number).setPoisonDie(true);
            poisonNum--;
        } else if (type == WitchMedicine.LIVE && liveNum != 0) {
            players.get(number).setAlive(true);
            liveNum--;
        }
    }
}
