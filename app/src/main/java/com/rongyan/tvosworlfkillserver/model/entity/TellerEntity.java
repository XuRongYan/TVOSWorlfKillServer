package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.model.GameManager;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.Role;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.TellerFunction;

import java.util.Map;

import static com.rongyan.tvosworlfkillserver.model.enums.RoleType.GOD;

/**
 * Created by XRY on 2017/7/25.
 */

public class TellerEntity extends Role implements TellerFunction {

    public TellerEntity(Map<Integer, Role> players) {
        super(players);
        good = true;
        setTag(GOD);
    }

    @Override
    public void setManagerIndex() {
        if (id != -1) {
            GameManager.setTellerId(id);
        }
    }

    @Override
    public boolean isGood(int number) {
        return players.get(number).isGood();
    }
}
