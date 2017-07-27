package com.rongyan.tvosworlfkillserver;

import com.rongyan.tvosworlfkillserver.model.entity.UserEntity;

/**
 * Created by XRY on 2017/7/27.
 */

public interface GodContract {
    void vote(UserEntity userEntity, int id); //往投票池中添加票数

    void kill(UserEntity userEntity, int id); //杀人漂池中添加票数

    void checkEveryDayStatus(); //报夜

    void everyOneCloseEyes(); //天黑请闭眼

    void guardOpenEyes(); //守卫请睁眼

    void guardCloseEyes(); //守卫请闭眼

    void wolvesOpenEyes(); //狼人请睁眼

    void wolvesCloseEyes(); //狼人请闭眼

    void tellerOpenEyes(); //预言家请睁眼

    void tellerCloseEyes(); //预言家请闭眼

    void witchOpenEyes(); //女巫请睁眼

    void witchCloseEyes(); //女巫请闭眼

    void hunterOpenEyes(); //猎人请睁眼

    void hunterCloseEyes(); //猎人请闭眼

    void everyoneOpenEyes(); //天亮了

    void askWitch(); //给女巫发消息

    void askPoison(); //要毒谁

    void askTeller(); //预言家要验谁的身份

    void askGuard(); //守卫要守谁

    void askIdiot();

    void askHunter(); //给开枪状态

    void askWolves(); //狼人要杀谁

    void chiefCampaign(); //警长竞选

    void banishVote(); //放逐投票

    void chiefVote(); //警长投票

    void tellGoodOrNot(); //告诉预言家验人结果
}
