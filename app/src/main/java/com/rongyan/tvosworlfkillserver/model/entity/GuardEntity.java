package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.model.GameManager;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.GuardFunction;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.Role;

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
    public void setManagerIndex() {
        if (id != -1) {
            GameManager.setGuardId(id);
        }
    }

    @Override
    public void protect(int number) {
        players.get(number).setProtect(true);
    }
}
