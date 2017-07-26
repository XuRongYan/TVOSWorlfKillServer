package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.GuardFunction;
import com.rongyan.tvosworlfkillserver.Model.Role;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class GuardEntity extends Role implements GuardFunction {

    public GuardEntity(Map<Integer, Role> players) {
        super(players);
        good = true;
    }

    @Override
    public void protect(int number) {
        players.get(number).setProtect(true);
    }
}
