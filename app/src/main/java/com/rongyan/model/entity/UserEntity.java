package com.rongyan.model.entity;


import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.enums.UserEventType;
import com.rongyan.model.state.OpenEyesState;

import java.io.Serializable;
import java.util.Arrays;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/7/27.
 */

public class UserEntity implements Serializable{
    private int userId;

    private String username;

    private byte[] headImg;

    //private God god;

    private RoleType roleType;

    private BaseState state = new OpenEyesState(); //基础状态是睁眼

    private boolean speeching = false;

    private UserEntity next = null;

    private UserEntity prev = null;



    public UserEntity(int userId, String username, byte[] headImg) {
        this.userId = userId;
        this.username = username;
        this.headImg = headImg;

    }

    public UserEntity(String username, byte[] headImg) {
        this.username = username;
        this.headImg = headImg;
    }

    /**
     * 自爆，仅狼人可用
     */
    public void selfDestruction() {
        if (roleType != RoleType.WOLF) {
            return;
        }
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.SELF_DESTRUCTION, -1));
    }

    public boolean isSpeeching() {
        return speeching;
    }

    public void setSpeeching(boolean speeching) {
        this.speeching = speeching;
    }

    public UserEntity getNext() {
        return next;
    }

    public void setNext(UserEntity next) {
        this.next = next;
    }

    public UserEntity getPrev() {
        return prev;
    }

    public void setPrev(UserEntity prev) {
        this.prev = prev;
    }

    public BaseState getState() {
        return state;
    }

    public void setState(BaseState state) {
        this.state = state;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getHeadImg() {
        return headImg;
    }

    public void setHeadImg(byte[] headImg) {
        this.headImg = headImg;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public void send(int targetId) {
        state.send(this, targetId);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (userId != that.userId) return false;
        if (speeching != that.speeching) return false;
        if (username != null ? !username.equals(that.username) : that.username != null)
            return false;
        if (!Arrays.equals(headImg, that.headImg)) return false;
        if (roleType != that.roleType) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (next != null ? !next.equals(that.next) : that.next != null) return false;
        return prev != null ? prev.equals(that.prev) : that.prev == null;

    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", roleType=" + roleType +
                ", state=" + state +
                ", speeching=" + speeching +
                ", next=" + next +
                ", prev=" + prev +
                '}';
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(headImg);
        result = 31 * result + (roleType != null ? roleType.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (speeching ? 1 : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        result = 31 * result + (prev != null ? prev.hashCode() : 0);
        return result;
    }
}
