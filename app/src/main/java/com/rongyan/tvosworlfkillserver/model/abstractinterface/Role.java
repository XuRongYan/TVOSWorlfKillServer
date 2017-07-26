package com.rongyan.tvosworlfkillserver.model.abstractinterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by XRY on 2017/7/25.
 */

public abstract class Role implements RoleFunction{
    protected int id = -1;
    protected Map<Integer, Role> players;
    protected boolean alive = true;
    protected int votedNumber = 0;
    protected int checkedKill = 0;
    protected boolean good;
    protected boolean protect = false;
    private boolean voteDie = false;
    private boolean killDie = false;
    private boolean poisonDie = false;
    private boolean shootDie = false;
    private Role next;
    private Role prev;
    private boolean speeching = false;
    private boolean openEyes = false;
    private List<Integer> votedIds = new ArrayList<>();

    public Role(Map<Integer, Role> players) {
        this.players = players;
    }

    @Override
    public void vote(int number) {
        players.get(number).voted(id);
    }

    @Override
    public void  voted(int id) {
        votedNumber++;
        votedIds.add(id);
    }

    public abstract void setManagerIndex();

    public Map<Integer, Role> getPlayers() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVoteDie() {
        return voteDie;
    }

    public void setVoteDie(boolean voteDie) {
        this.voteDie = voteDie;
    }

    public boolean isKillDie() {
        return killDie;
    }

    public void setKillDie(boolean killDie) {
        this.killDie = killDie;
    }

    public boolean isPoisonDie() {
        return poisonDie;
    }

    public void setPoisonDie(boolean poisonDie) {
        this.poisonDie = poisonDie;
    }

    public boolean isShootDie() {
        return shootDie;
    }

    public void setShootDie(boolean shootDie) {
        this.shootDie = shootDie;
    }

    public List<Integer> getVotedIds() {
        return votedIds;
    }

    public void setVotedIds(List<Integer> votedIds) {
        this.votedIds = votedIds;
    }

    public Role getNext() {
        return next;
    }

    public void setNext(Role next) {
        this.next = next;
    }

    public Role getPrev() {
        return prev;
    }

    public void setPrev(Role prev) {
        this.prev = prev;
    }

    public boolean isSpeeching() {
        return speeching;
    }

    public void setSpeeching(boolean speeching) {
        this.speeching = speeching;
    }

    public boolean isOpenEyes() {
        return openEyes;
    }

    public void setOpenEyes(boolean openEyes) {
        this.openEyes = openEyes;
    }
}
