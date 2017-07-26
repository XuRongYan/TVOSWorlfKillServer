package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.Role;
import com.rongyan.tvosworlfkillserver.Model.WitchFunction;
import com.rongyan.tvosworlfkillserver.Model.WitchMedicine;

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
    public void poisonOrLive(WitchMedicine type, int number) {
        if (type == WitchMedicine.POISON && poisonNum != 0) {
            players.get(number).setAlive(false);
        } else if (type == WitchMedicine.LIVE && liveNum != 0) {
            players.get(number).setAlive(true);
        }
    }
}
