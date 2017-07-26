package com.rongyan.tvosworlfkillserver.model.abstractinterface;

import com.rongyan.tvosworlfkillserver.model.enums.WitchMedicine;

/**
 * 女巫抽象
 * Created by XRY on 2017/7/25.
 */

public interface WitchFunction extends RoleFunction{
    /**
     * 开毒或用解药
     * @param type 毒药或者解药
     * @param number 用药的角色ID
     */
    void poisonOrLive(WitchMedicine type, int number);
}
