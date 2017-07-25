package com.rongyan.tvosworlfkillserver.Model.entity;

import com.rongyan.tvosworlfkillserver.Model.Role;
import com.rongyan.tvosworlfkillserver.Model.WorlfFunction;

import java.util.List;

/**
 * Created by XRY on 2017/7/25.
 */

public class WorlfEntity extends Role implements WorlfFunction {

    public WorlfEntity(List<Role> players) {
        super(players);
        good = false;
    }

    @Override
    public void kill(int number) {
        players.get(number).setCheckedKill(getCheckedKill() + 1);
    }
}
