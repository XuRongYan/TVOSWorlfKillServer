package com.rongyan.model.abstractinterface;


import com.rongyan.model.entity.UserEntity;

import java.io.Serializable;

/**
 * 状态接口想法是上帝每发送一次消息，接收消息的玩家就切换一次状态，切换状态以后
 * 可以向上帝发出不同的消息来告诉上帝要干什么
 * Created by XRY on 2017/7/28.
 */

public interface BaseState extends Serializable{
    void send(UserEntity userEntity, int targetId);
}
