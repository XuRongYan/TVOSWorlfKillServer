package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.model.enums.UserEventType;

/**
 * Created by XRY on 2017/7/27.
 */

public class UserEventEntity {
    private UserEntity send;

    private UserEventType type;

    private int target;

    public UserEventEntity(UserEntity send, UserEventType type, int target) {
        this.send = send;
        this.type = type;
        this.target = target;
    }

    public UserEntity getSend() {
        return send;
    }

    public void setSend(UserEntity send) {
        this.send = send;
    }

    public UserEventType getType() {
        return type;
    }

    public void setType(UserEventType type) {
        this.type = type;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }


}
