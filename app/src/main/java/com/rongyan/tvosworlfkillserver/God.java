package com.rongyan.tvosworlfkillserver;

import android.util.ArrayMap;

import com.rongyan.tvosworlfkillserver.model.entity.JesusEventEntity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEventEntity;
import com.rongyan.tvosworlfkillserver.model.enums.JesusEvent;
import com.rongyan.tvosworlfkillserver.model.enums.RoleType;

import java.util.LinkedHashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 上帝
 * Created by XRY on 2017/7/27.
 */

public class God implements GodContract{
    private static God INSTANCE = null;
    private Map<Integer, UserEntity> players = new LinkedHashMap<>(); //玩家的集合
    private Map<UserEntity, Integer> votePool = new ArrayMap<>(); //投票池
    private Map<UserEntity, Integer> killPool = new ArrayMap<>(); //杀人池
    private Map<Integer, UserEntity> chiefCampaignMap = new LinkedHashMap<>();
    public static final int GOOD = 1; //好人
    public static final int BAD = 0; //狼人
    private int killedId = -1; //狼人杀人ID
    private int tellId = -1; //预言家验人ID
    private int poisonId = -1; //毒人ID
    private int protectedId = -1; //守卫守人ID
    private int shootId = -1;
    private int prevProtectedId = -1; //记录上一次守卫守的人
    private boolean isIdiotVoted = false; //白痴是否被票
    private boolean isSave = false; //女巫是否选择救人
    private boolean hasPoison = true; //女巫是否有毒药
    private boolean hasLive = true; //女巫是否有解药

    private God() {
        EventBus.getDefault().register(this);
    }

    /**
     * 单例模式
     * @return
     */
    public static God getInstance() {
        if (INSTANCE == null) {
            synchronized (God.class) {
                if (INSTANCE == null) {
                    INSTANCE = new God();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void vote(UserEntity userEntity, int id) {
        votePool.put(userEntity, id);
    }

    @Override
    public void kill(UserEntity userEntity, int id) {
        killPool.put(userEntity, id);
    }

    @Override
    public void checkEveryDayStatus() {
        if (isSave && killedId == protectedId) { //同守同救
            dead(killedId);
        } else if (killedId != protectedId) { //
            dead(killedId);
        }
        dead(poisonId);


    }

    @Override
    public void everyOneCloseEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.CLOSE_EYES));
    }

    @Override
    public void guardOpenEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.GUARD, JesusEvent.OPEN_EYES));
    }

    @Override
    public void guardCloseEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.GUARD, JesusEvent.CLOSE_EYES));
    }

    @Override
    public void wolvesOpenEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.WOLF, JesusEvent.OPEN_EYES));
    }

    @Override
    public void wolvesCloseEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.WOLF, JesusEvent.CLOSE_EYES));
    }

    @Override
    public void tellerOpenEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.OPEN_EYES));
    }

    @Override
    public void tellerCloseEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.CLOSE_EYES));
    }

    @Override
    public void witchOpenEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.WITCH, JesusEvent.OPEN_EYES));
    }

    @Override
    public void witchCloseEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.WITCH, JesusEvent.CLOSE_EYES));
    }

    @Override
    public void hunterOpenEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.HUNTER, JesusEvent.OPEN_EYES));
    }

    @Override
    public void hunterCloseEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.HUNTER, JesusEvent.CLOSE_EYES));
    }

    @Override
    public void everyoneOpenEyes() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.OPEN_EYES));
    }

    @Override
    public void askWitch() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.WITCH, JesusEvent.SAVE, killedId));
    }

    @Override
    public void askPoison() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.SAVE));
    }

    @Override
    public void askTeller() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.GET));
    }

    @Override
    public void askGuard() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.GUARD, JesusEvent.PROTECT));
    }

    @Override
    public void askIdiot() {

    }

    @Override
    public void askHunter() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.HUNTER, JesusEvent.SHOOT));
    }

    @Override
    public void askWolves() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.WOLF, JesusEvent.KILL));
    }

    @Override
    public void chiefCampaign() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.CHIEF_CAMPAIGN));
    }

    @Override
    public void banishVote() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.VOTE));
    }

    @Override
    public void chiefVote() {
        EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.VOTE));
    }

    @Override
    public void tellGoodOrNot() {
        int goodOrNot = players.get(tellId).getRoleType() == RoleType.WOLF ? GOOD : BAD;
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.GOOD_OR_NOT, goodOrNot));
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEvent(UserEventEntity eventEntity) {
        switch (eventEntity.getType()) {
            case KILL: //杀人
                killPool.put(eventEntity.getSend(), eventEntity.getTarget());
                break;
            case VOTE: //票人
                votePool.put(eventEntity.getSend(), eventEntity.getTarget());
                break;
            case SHOOT: //开枪
                shoot(eventEntity.getTarget());
                break;
            case GET: //获取身份
                tellGoodOrNot();
                break;
            case SAVE:
                isSave = true;
                break;
            case POISON:
                poisonId = eventEntity.getTarget();
                break;
            case PROTECT:
                protectedId = eventEntity.getTarget();
                break;
        }
    }

    private void shoot(int id) {
        shootId = id;
        dead(id);
    }

    private void dead(int id) {
        if (id != -1) {
            players.get(id).setAlive(false);
        }
    }
}
