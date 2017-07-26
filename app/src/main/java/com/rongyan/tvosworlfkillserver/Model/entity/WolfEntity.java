package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.Role;
import com.rongyan.tvosworlfkillserver.Model.WolfFunction;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class WolfEntity extends Role implements WolfFunction {

    public WolfEntity(Map<Integer, Role> players) {
        super(players);
        good = false;
    }

    @Override
    public void kill(int number) {
        players.get(number).setCheckedKill(getCheckedKill() + 1);
    }
}
