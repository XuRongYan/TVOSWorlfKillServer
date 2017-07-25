package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.Role;
import com.rongyan.tvosworlfkillserver.Model.HunterFunction;

import java.util.List;

/**
 * Created by XRY on 2017/7/25.
 */

public class HunterEntity extends Role implements HunterFunction {

    public HunterEntity(List<Role> players) {
        super(players);
        good = true;
    }

    @Override
    public void shoot(int number) {
        players.get(number).setAlive(false);
    }
}
