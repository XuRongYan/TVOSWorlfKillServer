package com.rongyan.model.enums;

import java.io.Serializable;

/**
 * Created by XRY on 2017/7/27.
 */

public enum JesusEvent implements Serializable{
    CLOSE_EYES,//闭眼
    OPEN_EYES, //睁眼
    KILL, //杀人
    PROTECT, //守人
    POISON, //毒人
    SAVE, //救人
    SHOOT, //开枪带人
    TRUE, //好人
    FALSE, //狼人
    GET, //验人
    CHIEF_CAMPAIGN, //警长竞选
    VOTE, //投票
    SPEECH, //发言
    STOP_SPEECH, //停止发言
    GOOD_OR_NOT, //预言家验人结果
    DEAD, //死了
    POISON_DEAD ,//毒死
    SHOOT_STATE,
    CHIEF_VOTE, //竞选投票
    CHIEF_SPEECH,//竞选发言
    GET_SHOOT_STATE, //获取开枪状态
    YOU_ARE_CHIEF, //通知某人当选
    CHOOSE_SEQUENCE, //选择发言顺序
    GIVE_CHIEF, //移交警徽
    IDIOT_VOTED, //白痴被票
}
