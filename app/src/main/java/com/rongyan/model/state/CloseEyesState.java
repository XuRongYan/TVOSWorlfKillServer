package com.rongyan.model.state;


import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.entity.UserEntity;

/**
 * 闭眼状态
 * Created by XRY on 2017/7/28.
 */

public class CloseEyesState implements BaseState {
    @Override
    public void send(UserEntity userEntity, int targetId) {

    }
}
