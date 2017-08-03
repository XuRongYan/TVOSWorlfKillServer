package com.rongyan.model.entity;


import com.rongyan.model.enums.UserEventType;

import java.io.Serializable;

/**
 * Created by XRY on 2017/7/27.
 */

public class UserEventEntity implements Serializable {
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

    @Override
    public String toString() {
        return "UserEventEntity{" +
                "send=" + send +
                ", type=" + type +
                ", target=" + target +
                '}';
    }
}
