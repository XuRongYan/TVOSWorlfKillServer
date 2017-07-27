package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.model.GameManager;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.IdiotFunction;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.Role;
import com.rongyan.tvosworlfkillserver.model.enums.RoleType;

import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public class IdiotEntity extends Role implements IdiotFunction {
    private boolean firstVoted = true;
    public IdiotEntity(Map<Integer, Role> players) {
        super(players);
        good = true;
        setTag(RoleType.GOD);
    }

    @Override
    public void setManagerIndex() {
        if (id != -1) {
            GameManager.setIdiotId(id);
        }
    }

    public boolean isFirstVoted() {
        return firstVoted;
    }

    public void setFirstVoted(boolean firstVoted) {
        this.firstVoted = firstVoted;
    }
}
