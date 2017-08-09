package com.rongyan.tvosworlfkillserver;

import com.rongyan.model.entity.UserEntity;

/**
 * Created by XRY on 2017/7/27.
 */

public interface GodContract {
    void vote(UserEntity userEntity, int id); //往投票池中添加票数

    void kill(UserEntity userEntity, int id); //杀人漂池中添加票数

    void checkEveryDayStatus(); //报夜



    void tellGoodOrNot(int id); //告诉预言家验人结果
}
