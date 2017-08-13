package com.rongyan.model.enums;

import java.io.Serializable;

/**
 * Created by XRY on 2017/7/27.
 */

public enum  UserEventType implements Serializable{
    KILL, //刀人
    VOTE, //票人
    SHOOT, //开枪打人
    GET, //预言家验人
    SAVE, //女巫开解药
    POISON, //女巫开毒药
    NOT_SAVE, //女巫不救人
    PROTECT, //守卫守人
    CHIEF_CAMPAIGN, //上警
    NOT_CHIEF_CAMPAIGN, //不上警
    END_SPEECH, //结束发言
    CHOOSE_SEQUENCE, //选择发言顺序
    SELF_DESTRUCTION, //自爆（只有狼人可以自爆）
}
