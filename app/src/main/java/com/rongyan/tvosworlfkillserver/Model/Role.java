package com.rongyan.tvosworlfkillserver.Model;

import java.util.List;

/**
 * Created by XRY on 2017/7/25.
 */

public abstract class Role implements RoleFunction{
    protected List<Role> players;
    protected boolean alive = true;
    protected int votedNumber = 0;
    protected int checkedKill = 0;
    protected boolean good;
    protected boolean protect = false;

    public Role(List<Role> players) {
        this.players = players;
    }

    @Override
    public void vote(int number) {
        players.get(number).voted();
    }

    @Override
    public void  voted() {
        votedNumber++;
    }

    public List<Role> getPlayers() {
        return players;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getVotedNumber() {
        return votedNumber;
    }

    public void setVotedNumber(int votedNumber) {
        this.votedNumber = votedNumber;
    }

    public int getCheckedKill() {
        return checkedKill;
    }

    public void setCheckedKill(int checkedKill) {
        this.checkedKill = checkedKill;
    }

    public boolean isGood() {
        return good;
    }

    public boolean isProtect() {
        return protect;
    }

    public void setProtect(boolean protect) {
        this.protect = protect;
    }
}
