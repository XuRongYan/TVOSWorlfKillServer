package com.rongyan.tvosworlfkillserver;

import android.util.ArrayMap;

import com.rongyan.tvosworlfkillserver.exceptions.PlayerNotFitException;
import com.rongyan.tvosworlfkillserver.model.abstractinterface.BaseJesusState;
import com.rongyan.tvosworlfkillserver.model.entity.JesusEventEntity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEventEntity;
import com.rongyan.tvosworlfkillserver.model.enums.JesusEvent;
import com.rongyan.tvosworlfkillserver.model.enums.RoleType;
import com.rongyan.tvosworlfkillserver.model.state.DeadState;
import com.rongyan.tvosworlfkillserver.model.state.jesusstate.DaytimeState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 上帝
 * Created by XRY on 2017/7/27.
 */

public class God implements GodContract {
    private static God INSTANCE = null;
    private static List<RoleType> list = new ArrayList<>(); //牌堆
    private Map<Integer, UserEntity> players = new LinkedHashMap<>(); //玩家的集合
    private Map<UserEntity, Integer> votePool = new ArrayMap<>(); //投票池
    private Map<UserEntity, Integer> killPool = new ArrayMap<>(); //杀人池
    private Map<Integer, UserEntity> chiefCampaignMap = new LinkedHashMap<>();
    private static int wolfNum = 0;
    private static int villagerNum = 0;
    private static int tellerNum = 0;
    private static int witchNum = 0;
    private static int hunterNum = 0;
    private static int idiotNum = 0;
    private static int guardNum = 0;
    private int speechingId = -1; //正在发言
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
    private BaseJesusState state = new DaytimeState();
    Random random = new Random();

    private God(Map<Integer, UserEntity> players) {
        this.players = players;
        EventBus.getDefault().register(this);
        //发牌
        for (int i = 0; i < players.size(); i++) {
            UserEntity userEntity = players.get(i);
            EventBus.getDefault().register(userEntity);
            int i1 = random.nextInt() % (12 - i);
            userEntity.setRoleType(list.get(i1));
            list.remove(i1);
        }
    }

    /**
     * 单例模式
     *
     * @return
     */
    private static God getInstance(Map<Integer, UserEntity> players) {
        if (INSTANCE == null) {
            synchronized (God.class) {
                if (INSTANCE == null) {
                    INSTANCE = new God(players);
                }
            }
        }
        return INSTANCE;
    }

    public BaseJesusState getState() {
        return state;
    }

    public void setState(BaseJesusState state) {
        this.state = state;
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
            case NOT_SAVE:
                isSave = false;
                break;
            case POISON:
                poisonId = eventEntity.getTarget();
                break;
            case PROTECT:
                protectedId = eventEntity.getTarget();
                break;
            case CHIEF_CAMPAIGN:
                chiefCampaignMap.put(chiefCampaignMap.size(), eventEntity.getSend());
                break;
            case NOT_CHIEF_CAMPAIGN:
                break;
            case END_SPEECH:
                break;
        }
    }

    /**
     * 统一取消eventBus监听
     */
    public void release() {
        for (int i = 0; i < players.size(); i++) {
            EventBus.getDefault().unregister(players.get(i));
        }
        EventBus.getDefault().unregister(this);
    }

    private void shoot(int id) {
        shootId = id;
        dead(id);
    }

    private void dead(int id) {
        if (id != -1) {
            players.get(id).setState(new DeadState());
        }
    }

    public static class Builder {
        private Map<Integer, UserEntity> players;

        public Builder(Map<Integer, UserEntity> players) {
            this.players = players;
        }

        public Builder setVillagers(int num) {
            villagerNum = num;
            return this;
        }

        public Builder setWolves(int num) {
            wolfNum = num;
            return this;
        }

        public Builder setTeller() {
            tellerNum = 1;
            return this;
        }

        public Builder setWitch() {
            witchNum = 1;
            return this;
        }

        public Builder setHunter() {
            hunterNum = 1;
            return this;
        }

        public Builder setGuard() {
            guardNum = 1;
            return this;
        }

        public Builder setIdiot() {
            idiotNum = 1;
            return this;
        }

        public God build() throws PlayerNotFitException {
            int number = villagerNum + wolfNum + hunterNum + tellerNum + witchNum + idiotNum + guardNum;
            if (players.size() == number) {
                for (int i = 0; i < villagerNum; i++) {
                    list.add(RoleType.VILLAGER);
                }
                for (int i = 0; i < wolfNum; i++) {
                    list.add(RoleType.WOLF);
                }
                for (int i = 0; i < tellerNum; i++) {
                    list.add(RoleType.TELLER);
                }
                for (int i = 0; i < witchNum; i++) {
                    list.add(RoleType.WITCH);
                }
                for (int i = 0; i < hunterNum; i++) {
                    list.add(RoleType.HUNTER);
                }
                for (int i = 0; i < idiotNum; i++) {
                    list.add(RoleType.IDIOT);
                }
                for (int i = 0; i < guardNum; i++) {
                    list.add(RoleType.GUARD);
                }
                return getInstance(players);
            }

            else {
                throw new PlayerNotFitException("Player not fit,expect:" + players.size() + " but:" + number);
            }
        }
    }
}
