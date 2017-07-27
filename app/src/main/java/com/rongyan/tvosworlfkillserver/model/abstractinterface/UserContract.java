package com.rongyan.tvosworlfkillserver.model.abstractinterface;

/**
 * Created by XRY on 2017/7/27.
 */

public interface UserContract {
    void kill(int id);
    void vote(int id);
    void shoot(int id);
    void get(int id);
    void save();
    void poison(int id);
    void protect(int id);
    void chiefCampaign();
}
