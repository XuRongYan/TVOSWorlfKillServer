package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.model.GameManager;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.HunterFunction;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.Role;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class HunterEntity extends Role implements HunterFunction {

    public HunterEntity(Map<Integer, Role> players) {
        super(players);
        good = true;
    }

    @Override
    public void setManagerIndex() {
        if (id != -1) {
            GameManager.setHunterId(id);
        }
    }

    @Override
    public void shoot(int number) {
        players.get(number).setAlive(false);
    }
}