package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.Role;
import com.rongyan.tvosworlfkillserver.Model.VillagerFunction;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class VillagerEntity extends Role implements VillagerFunction {
    public VillagerEntity(Map<Integer, Role> players) {
        super(players);
        good = true;
    }
}
