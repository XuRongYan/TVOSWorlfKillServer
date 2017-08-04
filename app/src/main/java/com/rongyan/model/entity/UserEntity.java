package com.rongyan.model.entity;


import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.state.OpenEyesState;

import java.io.Serializable;

/**
 * Created by XRY on 2017/7/27.
 */

public class UserEntity implements Serializable{
    private int userId;

    private String username;

    private int headImg;

    //private God god;

    private RoleType roleType;

    private BaseState state = new OpenEyesState(); //基础状态是睁眼


    public UserEntity(int userId, String username, int headImg) {
        this.userId = userId;
        this.username = username;
        this.headImg = headImg;

    }

    public UserEntity(String username, int headImg) {
        this.username = username;
        this.headImg = headImg;
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

    public int getHeadImg() {
        return headImg;
    }

    public void setHeadImg(int headImg) {
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
        if (headImg != that.headImg) return false;
        return username != null ? username.equals(that.username) : that.username == null;

    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + headImg;
        return result;
    }



}
