package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.model.GameManager;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.Role;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.VillagerFunction;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class VillagerEntity extends Role implements VillagerFunction {
    public VillagerEntity(Map<Integer, Role> players) {
        super(players);
        good = true;
    }

    @Override
    public void setManagerIndex() {
        if (id != -1) {
            GameManager.getVillagerIds().add(id);
        }
    }
}
