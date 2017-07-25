package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.Role;
import com.rongyan.tvosworlfkillserver.Model.IdiotFunction;

import java.util.List;

/**
 * Created by XRY on 2017/7/25.
 */

public class IdiotEntity extends Role implements IdiotFunction {
    public IdiotEntity(List<Role> players) {
        super(players);
    }
}
