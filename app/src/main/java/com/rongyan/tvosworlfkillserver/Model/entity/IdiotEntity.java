package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.IdiotFunction;
import com.rongyan.tvosworlfkillserver.Model.Role;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class IdiotEntity extends Role implements IdiotFunction {
    public IdiotEntity(Map<Integer, Role> players) {
        super(players);
    }
}
