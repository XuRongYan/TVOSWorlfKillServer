package com.rongyan.tvosworlfkillserver;

import android.os.AsyncTask;
import android.util.ArrayMap;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.entity.UserEntity;
import com.rongyan.model.entity.UserEventEntity;
import com.rongyan.model.enums.DayTime;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.state.DeadState;
import com.rongyan.model.state.jesusstate.DaytimeState;
import com.rongyan.model.state.jesusstate.GuardCloseEyesState;
import com.rongyan.model.state.jesusstate.GuardOpenEyesState;
import com.rongyan.model.state.jesusstate.GuardProtectState;
import com.rongyan.model.state.jesusstate.HunterCloseEyesState;
import com.rongyan.model.state.jesusstate.HunterOpenEyesState;
import com.rongyan.model.state.jesusstate.HunterShootState;
import com.rongyan.model.state.jesusstate.KillingState;
import com.rongyan.model.state.jesusstate.NightState;
import com.rongyan.model.state.jesusstate.TellerCloseEyesState;
import com.rongyan.model.state.jesusstate.TellerGetState;
import com.rongyan.model.state.jesusstate.TellerOpenEyesState;
import com.rongyan.model.state.jesusstate.VottingState;
import com.rongyan.model.state.jesusstate.WatchCardState;
import com.rongyan.model.state.jesusstate.WitchChooseState;
import com.rongyan.model.state.jesusstate.WitchCloseState;
import com.rongyan.model.state.jesusstate.WitchOpenEyes;
import com.rongyan.tvosworlfkillserver.exceptions.PlayerNotFitException;
import com.rongyant.commonlib.util.LogUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 上帝
 * Created by XRY on 2017/7/27.
 */

public class God implements GodContract {
    private static final String TAG = "God";
    private static God INSTANCE = null;
    private Map<Integer, UserEntity> players = new LinkedHashMap<>(); //玩家的集合
    private Map<UserEntity, Integer> votePool = new ArrayMap<>(); //投票池
    private Map<UserEntity, Integer> killPool = new ArrayMap<>(); //杀人池
    private Map<Integer, UserEntity> chiefCampaignMap = new LinkedHashMap<>(); //竞选警长的玩家
    private Map<Integer, UserEntity> deadPlayerMap = new LinkedHashMap<>(); //死掉的玩家
    private Map<Integer, UserEntity> notCampaignMap = new LinkedHashMap<>(); //不上警的玩家
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
    private int shootId = -1; //开枪的Id
    private int prevProtectedId = -1; //记录上一次守卫守的人
    private boolean isIdiotVoted = false; //白痴是否被票
    private boolean isSave = false; //女巫是否选择救人
    private boolean hasPoison = true; //女巫是否有毒药
    private boolean hasLive = true; //女巫是否有解药
    public static int dayNum = 0; //游戏进行的天数
    public static DayTime dayTime = DayTime.NIGHT; //白天或者黑夜
    private BaseJesusState state = new DaytimeState();
    private StateAsyncTask mAsyncTask; //用于异步计时的游戏进程控制


    private God(Map<Integer, UserEntity> players) {
        this.players = players;
        EventBus.getDefault().register(this);
        LogUtils.e(TAG, "onMessageEvent", "game start");
        startGame();
    }

    /**
     * 单例模式
     *
     * @return
     */
    public static God getInstance(Map<Integer, UserEntity> players) {
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

    /**
     * 初始化游戏数据
     */
    private void initGame() {
        dayNum = 1; //游戏来到第一天
        if (witchNum != 0) {
            //若有女巫，初始化药剂
            hasPoison = true;
            hasLive = true;
            speechingId = -1;
        }
        //清空投票和杀人池
        votePool.clear();
        killPool.clear();
        killedId = -1;
        tellId = -1;
        poisonId = -1;
        protectedId = -1;
        shootId = -1;
        prevProtectedId = protectedId;
    }

    public void setState(BaseJesusState state) {
        this.state = state;
        mAsyncTask = new StateAsyncTask();
        mAsyncTask.execute(); //启动一个新的异步任务
        onState(state);
    }

    private void onState(BaseJesusState state) {
        if (state instanceof NightState) {
            onNight();
            return;
        }
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
                if (killPool.size() == wolfNum) {
                    cancelTask();
                }
                break;
            case VOTE: //票人
                votePool.put(eventEntity.getSend(), eventEntity.getTarget());
                if (votePool.size() == players.size() - deadPlayerMap.size()) {

                    cancelTask();
                }
                break;
            case SHOOT: //开枪
                shoot(eventEntity.getTarget());

                cancelTask();
                break;
            case GET: //获取身份
                tellGoodOrNot();
                cancelTask();
                break;
            case SAVE:
                isSave = true;
                cancelTask();
                break;
            case NOT_SAVE:
                isSave = false;
                cancelTask();
                break;
            case POISON:
                poisonId = eventEntity.getTarget();
                cancelTask();
                break;
            case PROTECT:
                protectedId = eventEntity.getTarget();
                cancelTask();
                break;
            case CHIEF_CAMPAIGN:
                chiefCampaignMap.put(chiefCampaignMap.size(), eventEntity.getSend());
                if ((notCampaignMap.size() + chiefCampaignMap.size())
                        == (players.size() - deadPlayerMap.size())) {
                    cancelTask();
                }
                break;
            case NOT_CHIEF_CAMPAIGN:
                notCampaignMap.put(notCampaignMap.size(), eventEntity.getSend());
                if ((notCampaignMap.size() + chiefCampaignMap.size())
                        == (players.size() - deadPlayerMap.size())) {
                    cancelTask();
                }
                break;
            case END_SPEECH:
                break;
        }
    }
//    @Subscribe
//    public void onMessageEvent(MessageEvent event) {
//        if (event.getMessage().equals(MessageEvent.START_GAME_MESSAGE)) {
//
//        }
//    }

    private void startGame() {
        setState(new WatchCardState());
        state.send(0);
        initGame();
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
            deadPlayerMap.put(id, players.get(id));
        }
    }

    private void changeState() {
        BaseJesusState next = state.next();
        if (mAsyncTask != null && !mAsyncTask.isCancelled()) {
            mAsyncTask.cancel(true);
        }
        setState(next);
        //没有守卫
        if ((next instanceof GuardOpenEyesState || next instanceof GuardCloseEyesState || next instanceof GuardProtectState) && guardNum <= 0) {
            changeState();
        }
        if ((next instanceof TellerOpenEyesState || next instanceof TellerCloseEyesState || next instanceof TellerGetState) && tellerNum <= 0) {
            changeState();
        }
        if ((next instanceof WitchCloseState || next instanceof WitchChooseState || next instanceof WitchOpenEyes) && witchNum <= 0) {
            changeState();
        }
        if ((next instanceof HunterOpenEyesState || next instanceof HunterCloseEyesState || next instanceof HunterShootState) && hunterNum <= 0) {
            changeState();
        }

    }

    /**
     * 入夜时初始化一些值
     */
    private void onNight() {
        killPool.clear();
        votePool.clear();
    }

    /**
     * 白天时初始化一些值
     */
    private void onMorning() {

    }

    private void cancelTask() {
        if (mAsyncTask != null) {
            mAsyncTask.cancel(true);
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
//            if (players.size() == number) {
//                return getInstance(players);
//            }
//
//            else {
//                throw new PlayerNotFitException("Player not fit,expect:" + players.size() + " but:" + number);
//            }
            return getInstance(players);
        }
    }

    public class StateAsyncTask extends AsyncTask<Void, Integer, Void> {
        private static final String TAG = "StateAsyncTask";
        public int stateDuration = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (state instanceof WatchCardState) {
                //看牌的持续时间
                stateDuration = 10;
            }
            if (state instanceof NightState) {
                //天黑请闭眼持续时间
                stateDuration = 5;
            }
            if (state instanceof KillingState) {
                //狼人杀人讨论
                stateDuration = 60;
            }
            if (state instanceof WitchChooseState) {
                //女巫行动
                stateDuration = 30;
            }
            if (state instanceof VottingState) {
                //投票
                stateDuration = 10;
            }
            if (state instanceof TellerGetState) {
                //预言家验人阶段
                stateDuration = 15;
            }
            if (state instanceof HunterShootState) {
                //猎人杀人阶段
                stateDuration = 15;
            }
            if (state instanceof GuardProtectState) {
                //守卫守人阶段
                stateDuration = 30;
            }
        }

        @Override
        protected Void doInBackground(Void...params) {
            for (int i = 0; i < stateDuration; i++) {
                if (isCancelled()) {
                    LogUtils.e(TAG, "onProgressUpdate", "truly cancelled");
                    return null;
                }
                try {
                    Thread.sleep(1000);
                    //更新进度
                    publishProgress((int)((i * 1.0 / stateDuration) * 100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //完成了就切换状态
            changeState();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           super.onProgressUpdate(values);
            if (isCancelled()) {
                LogUtils.e(TAG, "onProgressUpdate", "truly cancelled");
                return;
            }
            //更新UI
            LogUtils.e(TAG, "onProgressUpdate", values[0].toString());
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            //取消也切换状态
            LogUtils.e(TAG, "onCancelled", "try to cancel task");
            changeState();
        }
    }
}
