package com.rongyan.model.entity;


import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.state.ChiefCampaignState;
import com.rongyan.model.state.CloseEyesState;
import com.rongyan.model.state.DeadState;
import com.rongyan.model.state.GetState;
import com.rongyan.model.state.KillState;
import com.rongyan.model.state.OpenEyesState;
import com.rongyan.model.state.PoisonDeadState;
import com.rongyan.model.state.PoisonState;
import com.rongyan.model.state.ProtectState;
import com.rongyan.model.state.SaveState;
import com.rongyan.model.state.ShootState;
import com.rongyan.model.state.SpeechState;
import com.rongyan.model.state.VoteState;

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


    public void onMessageEvent(JesusEventEntity eventEntity) {
        //TODO 用户输入采用Rxjava的zip方法打包发送给God
        if (eventEntity.getRoleType() == roleType
                || eventEntity.getRoleType() == RoleType.ANY
                || (eventEntity.getRoleType() == RoleType.GOD && (roleType == RoleType.TELLER || roleType == RoleType.WITCH || roleType == RoleType.HUNTER || roleType == RoleType.IDIOT || roleType == RoleType.GUARD))) {
            switch (eventEntity.getEvent()) {
                case CLOSE_EYES:
                    setState(new CloseEyesState());
                    break;
                case OPEN_EYES:
                    setState(new OpenEyesState());
                    break;
                case KILL:
                    setState(new KillState());
                    break;
                case PROTECT:
                    setState(new ProtectState());
                    break;
                case POISON:
                    setState(new PoisonState());
                    break;
                case SAVE:
                    setState(new SaveState());
                    break;
                case SHOOT:
                    setState(new ShootState());
                    break;
                case TRUE:
                    break;
                case FALSE:
                    break;
                case GET:
                    setState(new GetState());
                    break;
                case CHIEF_CAMPAIGN:
                    setState(new ChiefCampaignState());
                    break;
                case VOTE:
                    setState(new VoteState());
                    break;
                case SPEECH:
                    setState(new SpeechState());
                    break;
                case STOP_SPEECH:
                    setState(new OpenEyesState());
                    break;
                case GOOD_OR_NOT:

                    break;
                case DEAD:
                    setState(new DeadState());
                    break;
                case POISON_DEAD:
                    setState(new PoisonDeadState());
                    break;
            }
        }
    }
}
