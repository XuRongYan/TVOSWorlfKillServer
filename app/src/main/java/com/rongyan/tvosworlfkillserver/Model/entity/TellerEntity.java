package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.Role;
import com.rongyan.tvosworlfkillserver.Model.TellerFunction;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class TellerEntity extends Role implements TellerFunction {

    public TellerEntity(Map<Integer, Role> players) {
        super(players);
        good = true;
    }

    @Override
    public boolean isGood(int number) {
        return players.get(number).isGood();
    }
}
