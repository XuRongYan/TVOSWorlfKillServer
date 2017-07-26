package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.model.GameManager;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.Role;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.WolfFunction;

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
    public void setManagerIndex() {
        if (id != -1) {
            GameManager.getWolfIds().add(id);
        }
    }

    @Override
    public void kill(int number) {
        players.get(number).setCheckedKill(getCheckedKill() + 1);
    }
}
