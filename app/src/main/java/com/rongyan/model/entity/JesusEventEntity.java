package com.rongyan.model.entity;


import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by XRY on 2017/7/27.
 */

public class JesusEventEntity implements Serializable{
    private RoleType roleType;

    private JesusEvent event;

    private int[] targetIds;

    public JesusEventEntity(RoleType roleType, JesusEvent event) {
        this.roleType = roleType;
        this.event = event;
    }

    public JesusEventEntity(RoleType roleType, JesusEvent event, int...targetIds) {
        this.roleType = roleType;
        this.event = event;
        this.targetIds = targetIds;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public JesusEvent getEvent() {
        return event;
    }

    public void setEvent(JesusEvent event) {
        this.event = event;
    }

    public int[] getTargetId() {
        return targetIds;
    }

    public void setTargetId(int[] targetId) {
        this.targetIds = targetId;
    }

    @Override
    public String toString() {
        return "JesusEventEntity{" +
                "roleType=" + roleType +
                ", event=" + event +
                ", targetId=" + Arrays.toString(targetIds) +
                '}';
    }
}
