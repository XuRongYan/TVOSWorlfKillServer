package com.rongyan.tvosworlfkillserver.entity;

/**
 * Created by XRY on 2017/8/2.
 */

public class GodCheck {
    private String godName;
    private boolean check;

    public GodCheck(String godName, boolean check) {
        this.godName = godName;
        this.check = check;
    }

    public String getGodName() {
        return godName;
    }

    public void setGodName(String godName) {
        this.godName = godName;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
