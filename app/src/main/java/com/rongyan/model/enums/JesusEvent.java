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
    SHOOT_STATE
}