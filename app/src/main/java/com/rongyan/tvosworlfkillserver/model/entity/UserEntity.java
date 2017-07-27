package com.rongyan.tvosworlfkillserver.model.entity;

import com.rongyan.tvosworlfkillserver.God;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.UserContract;
import com.rongyan.tvosworlfkillserver.model.enums.RoleType;
import com.rongyan.tvosworlfkillserver.model.enums.UserEventType;

import de.greenrobot.event.EventBus;

import static android.R.attr.id;

/**
 * Created by XRY on 2017/7/27.
 */

public class UserEntity implements UserContract {
    private int userId;

    private String username;

    private int headImg;

    private God god;

    private RoleType roleType;

    protected boolean alive = true;


    private boolean eyeOpen = true;


    @Override
    public void kill(int id) {
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.KILL, id));
    }

    public void vote(int number) {
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.VOTE, id));
    }

    @Override
    public void shoot(int id) {
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.SHOOT, id));
    }

    @Override
    public void get(int id) {
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.GET, id));
    }

    @Override
    public void save() {
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.SAVE, -1));
    }

    @Override
    public void poison(int id) {
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.POISON, id));
    }

    @Override
    public void protect(int id) {
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.PROTECT, id));
    }

    @Override
    public void chiefCampaign() {
        EventBus.getDefault().post(new UserEventEntity(this, UserEventType.CHIEF_CAMPAIGN, -1));
    }


    public UserEntity(int userId, String username, int headImg) {
        this.userId = userId;
        this.username = username;
        this.headImg = headImg;
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


    public boolean isEyeOpen() {
        return eyeOpen;
    }

    public void setEyeOpen(boolean eyeOpen) {
        this.eyeOpen = eyeOpen;
    }

    public void onMessageEvent(JesusEventEntity eventEntity) {
        //TODO 用户输入采用Rxjava的zip方法打包发送给God
        if (eventEntity.getRoleType() == roleType
                || eventEntity.getRoleType() == RoleType.ANY
                || (eventEntity.getRoleType() == RoleType.GOD && (roleType == RoleType.TELLER || roleType == RoleType.WITCH || roleType == RoleType.HUNTER || roleType == RoleType.IDIOT || roleType == RoleType.GUARD))) {
            switch (eventEntity.getEvent()) {
                case CLOSE_EYES:
                    eyeOpen = false;
                    break;
                case OPEN_EYES:
                    eyeOpen = true;
                    break;
                case KILL:

                    break;
                case PROTECT:
                    break;
                case POISON:
                    break;
                case SAVE:
                    break;
                case SHOOT:
                    break;
                case TRUE:
                    break;
                case FALSE:
                    break;
                case GET:
                    break;
                case CHIEF_CAMPAIGN:
                    break;
                case VOTE:
                    break;
                case SPEECH:
                    break;
                case STOP_SPEECH:
                    break;
                case GOOD_OR_NOT:
                    break;
            }
        }
    }
}
