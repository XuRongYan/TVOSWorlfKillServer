package com.rongyan.tvosworlfkillserver;

import android.os.AsyncTask;
import android.util.ArrayMap;

import com.rongyan.model.abstractinterface.BaseJesusState;
import com.rongyan.model.abstractinterface.BaseState;
import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.entity.UserEntity;
import com.rongyan.model.entity.UserEventEntity;
import com.rongyan.model.enums.DayTime;
import com.rongyan.model.enums.JesusEvent;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.enums.Sequential;
import com.rongyan.model.enums.SpeechSequence;
import com.rongyan.model.message.ConfirmMessage;
import com.rongyan.model.message.ToastMessage;
import com.rongyan.model.state.DeadState;
import com.rongyan.model.state.PoisonDeadState;
import com.rongyan.model.state.VoteState;
import com.rongyan.model.state.jesusstate.ChampaignSpeechState;
import com.rongyan.model.state.jesusstate.ChampaignVoteState;
import com.rongyan.model.state.jesusstate.ChiefCampaignState;
import com.rongyan.model.state.jesusstate.ChooseSequenceState;
import com.rongyan.model.state.jesusstate.DaytimeState;
import com.rongyan.model.state.jesusstate.GameEndState;
import com.rongyan.model.state.jesusstate.GuardCloseEyesState;
import com.rongyan.model.state.jesusstate.GuardOpenEyesState;
import com.rongyan.model.state.jesusstate.GuardProtectState;
import com.rongyan.model.state.jesusstate.HunterCloseEyesState;
import com.rongyan.model.state.jesusstate.HunterGetShootState;
import com.rongyan.model.state.jesusstate.HunterOpenEyesState;
import com.rongyan.model.state.jesusstate.HunterShootState;
import com.rongyan.model.state.jesusstate.KillingState;
import com.rongyan.model.state.jesusstate.LastWordsState;
import com.rongyan.model.state.jesusstate.NightState;
import com.rongyan.model.state.jesusstate.NotifyState;
import com.rongyan.model.state.jesusstate.SpeechState;
import com.rongyan.model.state.jesusstate.TellerCloseEyesState;
import com.rongyan.model.state.jesusstate.TellerGetState;
import com.rongyan.model.state.jesusstate.TellerOpenEyesState;
import com.rongyan.model.state.jesusstate.VottingState;
import com.rongyan.model.state.jesusstate.WatchCardState;
import com.rongyan.model.state.jesusstate.WitchChooseState;
import com.rongyan.model.state.jesusstate.WitchCloseState;
import com.rongyan.model.state.jesusstate.WitchOpenEyes;
import com.rongyan.tvosworlfkillserver.enums.GameMode;
import com.rongyan.tvosworlfkillserver.enums.GameResult;
import com.rongyan.tvosworlfkillserver.exceptions.PlayerNotFitException;
import com.rongyan.tvosworlfkillserver.message.GameResultMessage;
import com.rongyant.commonlib.util.LogUtils;
import com.rongyant.commonlib.util.TimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * 目前来看有以下几个问题：
 * 1.连接终端过多时，家庭的路由器可能承担不了如此巨大的开销，可能网络会因此崩溃 （好像暂时不用考虑了）
 * 2.长时间不操作客户端可能会出现ANR （已解决，原因是service）
 * 3.性能比较差的手机收到信号后的响应会很慢（也有网络原因），很可能造成游戏进度阻塞或者不同步
 * 4.游戏进程的控制有错误，发言这一块，要将map也传进去(已解决)
 * 5.要添加一个遗言阶段 （已解决）
 * 6.玩家在拿到牌的时候应该要知道自己的号码 (已解决)
 * 7.死人的时候向客户端发消息，客户端进行相应的处理（）已解决
 * 8.警长的选票（1.5票）（已解决）
 * 9.警长死亡的时候移交警徽(已解决)
 * 10.警上可以选择退水（已解决）
 * 11.狼人可以自爆，自爆直接进入黑夜(已解决)
 * 12.service ANR（已解决）
 * 13.发言流程有问题（已解决）
 * 14.已经上警的玩家应不参加投票（已解决）
 * 15.getMost算法有问题(解决并优化)
 * 16.报夜阶段没有日志提示(已解决)
 * 17.不显示自爆按钮(已解决)
 * 18.开始白天发言的时候不发言（已解决）
 * 19.getNear算法有问题（已解决）
 * 20.添加弃票逻辑
 * 21.添加死亡界面
 * 22.完善神的功能（白痴）
 */


/**
 * 上帝
 * Created by XRY on 2017/7/27.
 */

public class God implements GodContract {
    private static final String TAG = "God";
    public static final int SINGLE_DEATH = 0X0; //单死，死左死右
    public static final int DOUBLE_DEATH = 0X1; //双死，警左警右
    private static God INSTANCE = null;
    private Map<Integer, UserEntity> players = new LinkedHashMap<>(); //玩家的集合
    private Map<UserEntity, Integer> votePool = new ArrayMap<>(); //投票池
    private Map<UserEntity, Integer> killPool = new ArrayMap<>(); //杀人池
    private Map<Integer, UserEntity> chiefCampaignMap = new LinkedHashMap<>(); //竞选警长的玩家
    private Map<Integer, UserEntity> deadPlayerMap = new LinkedHashMap<>(); //死掉的玩家
    private Map<Integer, UserEntity> notCampaignMap = new LinkedHashMap<>(); //不上警的玩家
    private List<UserEntity> pkList;
    private static int wolfNum = 0;
    private static int villagerNum = 0;
    private static int tellerNum = 0;
    private static int witchNum = 0;
    private static int hunterNum = 0;
    private static int idiotNum = 0;
    private static int guardNum = 0;
    private static GameMode mode = GameMode.KILL_SIDE; //游戏模式
    private int speechingId = -1; //正在发言
    public static final int GOOD = 1; //好人
    public static final int BAD = 0; //狼人
    private int killedId = -1; //狼人杀人ID
    private int tellId = -1; //预言家验人ID
    private int poisonId = -1; //毒人ID
    private int protectedId = -1; //守卫守人ID
    private int shootId = -1; //开枪的Id
    private int idiotId = -1;
    private int voteId = -1;
    private int killId = -1;
    private int chiefId = -1; //警长Id
    private int prevProtectedId = -1; //记录上一次守卫守的人
    private boolean isIdiotVoted = false; //白痴是否被票
    private boolean isSave = false; //女巫是否选择救人
    private boolean hasPoison = true; //女巫是否有毒药
    private boolean hasLive = true; //女巫是否有解药
    public static int dayNum = 0; //游戏进行的天数
    public static DayTime dayTime = DayTime.NIGHT; //白天或者黑夜
    private BaseJesusState state = new DaytimeState();
    private StateAsyncTask mAsyncTask; //用于异步计时的游戏进程控制
    private SpeechAsyncTask mSpeechAsyncTask; //用于异步控制发言时间
    private int confirmNum = 0;
    //活着的三方势力数量
    private int liveWolfNum = 0;
    private int liveGodNum = 0;
    private int liveVillagerNum = 0;
    private GameResult result; //游戏结果
    private Sequential sequential = Sequential.CLOCKWISE; //顺时针还是逆时针
    private SpeechSequence speechSequence = SpeechSequence.TIME;
    private int hasSpeechNum = 0; //已经发言过的人
    private Random random = new Random();
    private BaseJesusState lastState;
    private boolean canSelfDestruction = true; //是否可以自爆
    private boolean hasVote = false;


    private God(Map<Integer, UserEntity> players) {
        this.players = players;
        EventBus.getDefault().register(this);
        for (int i = 0; i < this.players.size(); i++) {
            //定发言顺序
            this.players.get(i).setNext(players.get((i + 1) % players.size()));
            this.players.get(i).setPrev(players.get((players.size() - 1 + i) % players.size()));
            if (players.get(i).getRoleType() == RoleType.IDIOT) {
                idiotId = i;
            }
        }
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
        dayNum = 0; //游戏来到第一天
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

    private boolean isGameEnd() {
        getLiveNum();
        if (liveWolfNum == 0) {
            result = GameResult.WOLF_WIN;
            return true;
        }

        if (mode == GameMode.KILL_SIDE) {
            if (liveGodNum == 0 || liveVillagerNum == 0) {
                result = GameResult.WOLF_WIN;
                return true;
            }
        } else {
            if (liveVillagerNum == 0 && liveGodNum == 0) {
                result = GameResult.WOLF_WIN;
                return true;
            }
        }
        return false;
    }

    /**
     * 统计活着势力的数量
     */
    private void getLiveNum() {
        Collection<UserEntity> values = players.values();
        for (UserEntity value : values) {
            BaseState state = value.getState();
            //没死
            if (!(state instanceof DeadState) && !(state instanceof PoisonDeadState)) {
                RoleType roleType = value.getRoleType();
                switch (roleType) {
                    case VILLAGER:
                        liveVillagerNum++;
                        break;
                    case WOLF:
                        liveWolfNum++;
                        break;
                    default:
                        liveGodNum++;
                        break;
                }
            }
        }
    }

    public void setState(BaseJesusState state) {

        lastState = this.state;
        this.state = state;
        cancelTask();
        onState(state);
        if (state instanceof WitchChooseState) {
            int[] aliveIds = getAliveIds();
            int[] witchIds = new int[aliveIds.length + 2];

            witchIds[0] = killedId;  //第0位放被杀的id
            int drugState = 0x00;
            if (hasLive) {
                drugState |= 0x01;
            }
            if (hasPoison) {
                drugState |= 0x10;
            }
            witchIds[1] = drugState; //第1位放药的数量
            //后面几位放活着人的id
            for (int i = 2; i < witchIds.length; i++) {
                witchIds[i] = aliveIds[i - 2];
            }
            LogUtils.e(TAG, "witchids", Arrays.toString(witchIds));
            LogUtils.e(TAG, "aliveIds", Arrays.toString(aliveIds));
            state.send(witchIds);
        } else if (state instanceof HunterShootState) {
            boolean shootable;
            if (-1 == killedId) {
                shootable = false;
            } else if (players.get(killedId).getRoleType() == RoleType.HUNTER && poisonId != killedId) {
                shootable = true;
            } else {
                shootable = false;
            }
            state.send(shootable ? 1 : 0); //1表示可以开枪，0表示不能开枪
        } else if (state instanceof ChooseSequenceState) {
            if (chiefId == -1) {
                //没有警长就看天意吧
                speechSequence = SpeechSequence.TIME;
                sequential = Sequential.CLOCKWISE;
                changeState(); //切换状态（发言状态）
                startSpeech(players, true); //开始发言
            } else if (chiefId != -1 && poisonId != -1 && killedId != -1 && killedId != poisonId) {
                //双死
                state.send(chiefId, DOUBLE_DEATH);
            } else if (chiefId != -1 && ((killedId != -1 && poisonId == -1) || (killedId == -1 && poisonId != -1) || (poisonId != -1 && killedId == poisonId))) {
                //单死
                state.send(chiefId, SINGLE_DEATH);
            } else {
                //谁也没死警左警右
                state.send(chiefId, DOUBLE_DEATH);
            }

        } else if (state instanceof ChampaignVoteState) {
            Collection<UserEntity> values = chiefCampaignMap.values();
            int[] ids = new int[values.size()];
            int index = 0;
            for (UserEntity value : values) {
                ids[index++] = value.getUserId();
            }
            state.send(ids);
        } else if (state instanceof LastWordsState) {
            if (((LastWordsState) state).getId() != -1) {
                state.send(((LastWordsState) state).getId());
            } else {
                state.send(voteId);
            }
        } else {
            state.send(getAliveIds());

        }

    }

    private void onState(BaseJesusState state) {
        if (state instanceof NightState) {
            if (dayNum >= 1) {
                doOnGameEnd();
            }
            onNight();
            return;
        }
        if (state instanceof DaytimeState) {
            //dayNum++;
            if (dayNum != 1) {
                doOnGameEnd();
            }
            dayTime = DayTime.DAY_TIME;
            LogUtils.e(TAG, "onNight", "第" + dayNum + "天日");
            canSelfDestruction = true;
            return;
        }

        if (state instanceof GameEndState) {
            release();
            return;
        }
        if (state instanceof ChampaignSpeechState) {

            return;
        }
        if (state instanceof NotifyState) {
            checkEveryDayStatus();
            return;
        }
        if (state instanceof LastWordsState) {

        }

    }

    /**
     * 若游戏结束
     */
    private void doOnGameEnd() {
        if (isGameEnd()) {
            LogUtils.e(TAG, "doOnGameEnd", "游戏结束" + (result == GameResult.WOLF_WIN ? "狼人" : "好人") + "阵营胜利");
            setState(new GameEndState());
            EventBus.getDefault().post(new GameResultMessage(result, players));
            releaseGod();
        } else {
            LogUtils.e(TAG, "doOnGameEnd", "游戏继续");
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
        if (killedId == -1 && poisonId == -1) {
            LogUtils.e(TAG, "checkEveryDayStatus", "昨夜是平安夜");
        } else if ((killedId != -1 && poisonId == -1) || (killedId == -1 && poisonId != -1)) {
            LogUtils.e(TAG, "checkEveryDayStatus", "昨夜" + (killedId == -1 ? poisonId : killId) + "号玩家倒牌");
            if (dayNum == 1) {
                setState(new LastWordsState(killedId));
            }
        } else {
            int i = random.nextInt() % 1;
            LogUtils.e(TAG, "checkEveryDayStatus", "昨夜" + (i == 0 ? killedId + "," + poisonId : poisonId + "," + killedId) + "号玩家倒排");
            if (dayNum == 1) {
                setState(new LastWordsState(killedId));
            }
        }
        if (!(state instanceof LastWordsState)) {
            dead(killedId);
            dead(poisonId);
        }

    }


    @Override
    public void tellGoodOrNot(int id) {
        tellId = id;
        int goodOrNot = players.get(id).getRoleType() == RoleType.WOLF ? BAD : GOOD;
        EventBus.getDefault().post(new JesusEventEntity(RoleType.TELLER, JesusEvent.GOOD_OR_NOT, goodOrNot));
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEvent(ConfirmMessage message) {
        switch (message.getMessage()) {
            case ConfirmMessage.CONFIRM_WATCH_CARD:
                if (players.size() == ++confirmNum) {
                    confirmNum = 0;
                }
                break;
            case ConfirmMessage.CONFIRM:
                //收到确认信号再进行下一步
                LogUtils.e(TAG, "onConfirmEvent", "get receive");
                if (++confirmNum == players.size() - deadPlayerMap.size()) { //若所有存活的玩家发出确认信号players.size() - deadPlayerMap.size()
                    if (!(state instanceof GameEndState)) {
                        LogUtils.e(TAG, "onConfirmEvent", "start new asyncTask");
                        mAsyncTask = new StateAsyncTask();
                        mAsyncTask.execute(); //启动一个新的异步任务
                    }
                    confirmNum = 0;
                }
                break;

        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEvent(UserEventEntity eventEntity) {
        switch (eventEntity.getType()) {
            case KILL: //杀人
                killPool.put(eventEntity.getSend(), eventEntity.getTarget());
                if (killPool.size() == wolfNum) {
                    List<UserEntity> most = getMost(killPool);
                    if (most.size() == 1) {
                        killedId = most.get(0).getUserId();
                    }
                    cancelTask();
                }

                break;
            case VOTE: //票人
                LogUtils.e(TAG, "vote", (eventEntity.getSend().getUserId() + 1) + "号玩家投" + eventEntity.getTarget() + "号玩家");
                if (state instanceof VoteState) {
                    exileVote(eventEntity);
                } else if (state instanceof ChampaignVoteState) {
                    champaignVote(eventEntity);
                }
                break;
            case SHOOT: //开枪
                shoot(eventEntity.getTarget());
                shootId = eventEntity.getTarget();
                cancelTask();
                break;
            case GET: //获取身份
                tellGoodOrNot(eventEntity.getTarget());
                cancelTask();
                break;
            case SAVE:
                if (hasLive) {
                    isSave = true;
                } else {
                    isSave = false;
                }
                cancelTask();
                break;
            case NOT_SAVE:
                isSave = false;
                cancelTask();
                break;
            case POISON:
                if (hasPoison) {
                    poisonId = eventEntity.getTarget();
                } else {
                    poisonId = -1;
                }
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
                    startSpeech(chiefCampaignMap, false);
                }
                break;
            case NOT_CHIEF_CAMPAIGN:
                notCampaignMap.put(notCampaignMap.size(), eventEntity.getSend());
                if ((notCampaignMap.size() + chiefCampaignMap.size())
                        == (players.size() - deadPlayerMap.size())) {

                    cancelTask();
                    startSpeech(chiefCampaignMap, false);
                }
                break;
            case END_SPEECH:
                if (state instanceof LastWordsState) {
                    mAsyncTask.cancel(true);
                } else {
                    mSpeechAsyncTask.cancel(true);
                }
                break;
            case CHOOSE_SEQUENCE:
                switch (eventEntity.getTarget()) {
                    case 0x00:
                        //警左
                        speechSequence = SpeechSequence.CHIEF_LEFT;
                        sequential = Sequential.CLOCKWISE;
                        break;
                    case 0x01:
                        speechSequence = SpeechSequence.CHIEF_RIGHT;
                        sequential = Sequential.ANTI_CLOCKWISE;
                        //警右
                        break;
                    case 0x10:

                        //死左
                        speechSequence = SpeechSequence.DEAD_LEFT;
                        sequential = Sequential.CLOCKWISE;
                        break;
                    case 0x11:
                        //死右
                        speechSequence = SpeechSequence.DEAD_RIGHT;
                        sequential = Sequential.ANTI_CLOCKWISE;
                        break;

                }
                changeState();
                startSpeech(players, true);
                break;
            case GIVE_CHIEF:
                chiefId = eventEntity.getTarget();
                EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.YOU_ARE_CHIEF, chiefId));
                break;
            case NOT_GIVE_CHIEF:
                chiefId = -1;
                break;
            case RETURN_WATER:
                chiefCampaignMap.remove(eventEntity.getSend().getUserId());
                EventBus.getDefault().post(new ToastMessage(eventEntity.getSend().getUserId() + "号玩家退水"));
                break;
            case SELF_DESTRUCTION:
                synchronized (God.class) {
                    if (canSelfDestruction) {
                        dead(eventEntity.getSend().getUserId());
                        setState(new NightState());
                        cancelTask();
                        cancelSpeechTask();
                        canSelfDestruction = false;
                    }
                }
                break;
        }
    }

    private void champaignVote(UserEventEntity eventEntity) {
        votePool.put(eventEntity.getSend(), eventEntity.getTarget()); //XX玩家投XX号玩家
        if (votePool.size() == notCampaignMap.size()) {
            synchronized (God.class) {
                hasVote = true;
            }
            champaignVoteImpl();
            cancelTask();
        }
    }

    private void champaignVoteImpl() {
        List<UserEntity> most = getMost(votePool);
        if (most.size() > 1 && pkList == null) {
            Map<Integer, UserEntity> pkMap = new HashMap<>();

            for (UserEntity userEntity : pkList) {
                pkMap.put(userEntity.getUserId(), userEntity);
            }
            pkList = most;
            setState(new ChampaignSpeechState());
            LogUtils.e(TAG, "champaignVote", "平票，进入PK环节");
            startSpeech(pkMap, false);
        } else if (most.size() > 1 && pkList != null) {
            LogUtils.e(TAG, "champaignVote", "平票，没有警长");
            pkList = null;

            chiefId = -1;
        } else {
            LogUtils.e(TAG, "champaignVote", (most.get(0).getUserId() + 1) + "号玩家当选警长");
            chiefId = most.get(0).getUserId();
            EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.YOU_ARE_CHIEF, chiefId));
        }

    }

    /**
     * 放逐投票
     *
     * @param eventEntity
     */
    private void exileVote(UserEventEntity eventEntity) {
        votePool.put(eventEntity.getSend(), eventEntity.getTarget());
        if (votePool.size() == players.size() - deadPlayerMap.size()) {
            synchronized (God.class) {
                hasVote = true;
            }
            exileVoteImpl();
            cancelTask();
        }
    }

    private void exileVoteImpl() {
        List<UserEntity> most = getMost(votePool);
        if (most.size() > 1 && pkList == null) {
            Map<Integer, UserEntity> pkMap = new HashMap<>();
            for (UserEntity userEntity : pkList) {
                pkMap.put(userEntity.getUserId(), userEntity);
            }
            pkList = most;
            setState(new ChampaignSpeechState());
            LogUtils.e(TAG, "vote", "平票，进入PK环节");
            startSpeech(pkMap, false);
        } else if (most.size() > 1 && pkList != null) {
            LogUtils.e(TAG, "vote", "平票");
            pkList = null;
            voteId = -1;
        } else {
            LogUtils.e(TAG, "vote", (most.get(0).getUserId() + 1) + "号玩家出局");
            if (voteId == idiotId && idiotId != -1) {
                EventBus.getDefault().post(new JesusEventEntity(RoleType.IDIOT, JesusEvent.IDIOT_VOTED, voteId));
            }
            voteId = most.get(0).getUserId();
            dead(voteId);
        }
    }

    /**
     * 降序排列
     *
     * @param map 玩家投票的map，UserEntity为玩家
     * @return
     */
    private List<UserEntity> getMost(Map<UserEntity, Integer> map) {
        Set<UserEntity> userEntities = map.keySet(); //投票玩家的集合
        List<UserEntity> resultList = new ArrayList<>();
        int[] votes = new int[players.size()]; //用一个和玩家数量一样大小的数组表示被投的号数
        //遍历map，取出票数，数组对应位++
        for (UserEntity userEntity : userEntities) {
            Integer targetId = map.get(userEntity);
            if (userEntity.getUserId() == chiefId) { //若为警长
                votes[targetId] += 3; //警长1.5票，方便计算取两倍
            } else {
                votes[targetId] += 2; //其他人都是1票
            }
        }
        int[] votesCopy = Arrays.copyOf(votes, votes.length); //备份
        Arrays.sort(votesCopy); //升序排列
        int voteMax = votesCopy[votesCopy.length - 1]; //获取最大票数
        for (int i = 0; i < votes.length; i++) {
            if (voteMax == votes[i]) {
                resultList.add(players.get(i)); //把最高票数对应的人都添加进resultList
            }
        }
        return resultList;
    }

    private void startGame() {
        LogUtils.e(TAG, "onMessageEvent", "game start");
        setState(new WatchCardState());
        //state.send(0);
        initGame();
        mAsyncTask = new StateAsyncTask();
        mAsyncTask.execute(); //启动一个新的异步任务
        confirmNum = 0;
    }

    /**
     * 统一取消eventBus监听
     */
    public void release() {
        for (int i = 0; i < players.size(); i++) {
            EventBus.getDefault().unregister(players.get(i));
        }
        EventBus.getDefault().unregister(this);
        INSTANCE = null;
        LogUtils.e(TAG, "release", "release resource");
    }

    public void releaseGod() {
        release();
        INSTANCE = null;
        wolfNum = 0;
        villagerNum = 0;
        tellerNum = 0;
        witchNum = 0;
        hunterNum = 0;
        idiotNum = 0;
        guardNum = 0;
        mode = GameMode.KILL_SIDE; //游戏模式
        dayNum = 0; //游戏进行的天数
        dayTime = DayTime.NIGHT; //白天或者黑夜
    }

    private void shoot(int id) {
        shootId = id;
        dead(id);
    }

    private void dead(int id) {
        if (id != -1) {
            EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.DEAD, id));
            players.get(id).setState(new DeadState());
            deadPlayerMap.put(id, players.get(id));
            if (id == chiefId) {
                EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.GIVE_CHIEF, chiefId));
            }
        }
    }

    private void changeState() {

        BaseJesusState next = state.next();

        if (next != null) {
            setState(next);
            LogUtils.e(TAG, "changeState", "state has changed, state:" + next);
        }
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
        dayTime = DayTime.NIGHT;
        dayNum++;
        LogUtils.e(TAG, "onNight", "第" + dayNum + "天夜");
    }

    /**
     * d
     * 白天时初始化一些值
     */
    private void onMorning() {

    }

    private void cancelSpeechTask() {
        if (mSpeechAsyncTask != null) {
            mSpeechAsyncTask.cancel(true);
        }
    }

    private void cancelTask() {
        if (mAsyncTask != null && mAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            LogUtils.e(TAG, "onCancelled", "try to cancel task");
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

        public Builder setMode(GameMode mode) {
            God.mode = mode;
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

    public int[] getAliveIds() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            UserEntity userEntity = players.get(i);
            if (!(userEntity.getState() instanceof DeadState || userEntity.getState() instanceof PoisonDeadState)) {
                list.add(i);
            }
        }
        Integer[] array = new Integer[list.size()];
        Integer[] integers = list.toArray(array);
        int[] arrayInt = new int[integers.length];
        for (int i = 0; i < integers.length; i++) {
            arrayInt[i] = integers[i];
        }
        return arrayInt;
    }

    /**
     * 用于顺时针发言
     *
     * @param userEntity
     */
    private void nextSpeech(UserEntity userEntity, Map<Integer, UserEntity> map, boolean hasDead) {
        UserEntity nextUser = userEntity.getNext();
        BaseState state = userEntity.getNext().getState();
        //跳过死人
        while (state instanceof DeadState || state instanceof PoisonDeadState || !containsId(nextUser.getUserId(), map)) {
            if (hasDead) {
                hasSpeechNum++;
            }
            nextUser = nextUser.getNext();
            state = nextUser.getState();
        }
        cancelSpeechTask();
        mSpeechAsyncTask = new SpeechAsyncTask(nextUser, map, hasDead);
        mSpeechAsyncTask.execute();
    }

    private boolean containsId(Integer id, Map<Integer, UserEntity> map) {
        for (UserEntity userEntity : map.values()) {
            if (userEntity.getUserId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * 用于逆时针发言
     *
     * @param userEntity
     */
    private void prevSpeech(UserEntity userEntity, Map<Integer, UserEntity> map, boolean hasDead) {
        UserEntity prevUser = userEntity.getPrev();
        BaseState state = userEntity.getPrev().getState();
        //跳过死人
        while (state instanceof DeadState || state instanceof PoisonDeadState || !containsId(prevUser.getUserId(), map)) {
            if (hasDead) {
                hasSpeechNum++;
            }
            prevUser = prevUser.getPrev();
            state = prevUser.getState();
        }
        cancelSpeechTask();
        mSpeechAsyncTask = new SpeechAsyncTask(prevUser, map, hasDead);
        mSpeechAsyncTask.execute();
    }

    private void startSpeech(Map<Integer, UserEntity> map, boolean hasDead) {
        UserEntity userEntity = null;
        cancelTask();
        if (map == null) {
            LogUtils.e(TAG, "startSpeech", "map is null");
            changeState();
            return;
        }
        Collection<UserEntity> values = map.values();
        LogUtils.e(TAG, "startSpeech", Arrays.deepToString(values.toArray()));
        switch (speechSequence) {
            case CHIEF_LEFT:
                //TODO 做出了修改
                for (int i = 0; i < map.size(); i++) {
                    UserEntity userEntity1 = null;
                    for (int j = 0; j < map.size(); j++) {
                        userEntity1 = map.get(j);
                        if (userEntity1.getUserId() == (chiefId + i + 1) % map.size()) {
                            break;
                        } else {
                            userEntity1 = null;
                        }

                    }
                    if (userEntity1 != null) {
                        BaseState state = userEntity1.getState();
                        if (!(state instanceof DeadState) && !(state instanceof PoisonDeadState)) {
                            userEntity = userEntity1;
                            break;
                        }
                    }
                }
                break;
            case CHIEF_RIGHT:
                for (int i = 0; i < map.size(); i++) {
                    UserEntity userEntity1 = null;
                    for (int j = 0; j < map.size(); j++) {
                        userEntity1 = map.get(j);
                        if (userEntity1.getUserId() == (map.size() + chiefId - i - 1) % map.size()) {
                            break;
                        } else {
                            userEntity1 = null;
                        }

                    }

                    if (userEntity1 != null) {
                        BaseState state = userEntity1.getState();
                        if (!(state instanceof DeadState) && !(state instanceof PoisonDeadState)) {
                            userEntity = userEntity1;
                            break;
                        }
                    }
                }
                break;
            case DEAD_LEFT:
                for (int i = 0; i < map.size(); i++) {
                    UserEntity userEntity1 = null;
                    for (int j = 0; j < map.size(); j++) {
                        userEntity1 = map.get(j);
                        if (userEntity1.getUserId() == (killedId + i + 1) % map.size()) {
                            break;
                        } else {
                            userEntity1 = null;
                        }

                    }
                    if (userEntity1 != null) {
                        BaseState state = userEntity1.getState();
                        if (!(state instanceof DeadState) && !(state instanceof PoisonDeadState)) {
                            userEntity = userEntity1;
                            break;
                        }
                    }
                }
                break;
            case DEAD_RIGHT:
                for (int i = 0; i < map.size(); i++) {
                    UserEntity userEntity1 = null;
                    for (int j = 0; j < map.size(); j++) {
                        userEntity1 = map.get(j);
                        if (userEntity1.getUserId() == (map.size() + killedId - i - 1) % map.size()) {
                            break;
                        } else {
                            userEntity1 = null;
                        }

                    }
                    if (userEntity1 != null) {
                        BaseState state = userEntity1.getState();
                        if (!(state instanceof DeadState) && !(state instanceof PoisonDeadState)) {
                            userEntity = userEntity1;
                            break;
                        }
                    }
                }
                break;
            case TIME:
                String[] split = TimeUtils.getTimeInSecond(System.currentTimeMillis()).split(":");
                Integer integer = Integer.valueOf(split[1]) % 10; //取分钟
                UserEntity nearUser = getNearUser(integer, map);
                userEntity = nearUser;
                break;
        }
        if (userEntity != null) {
            mSpeechAsyncTask = new SpeechAsyncTask(userEntity, map, hasDead);
            mSpeechAsyncTask.execute();
        } else {
            LogUtils.e(TAG, "startSpeech", "user is null");
        }
    }

    /**
     * 获取和目标发言玩家距离最近的玩家
     *
     * @param integer id
     * @param map     发言的玩家集合
     * @return
     */
    @SuppressWarnings("unchecked")
    private UserEntity getNearUser(Integer integer, Map<Integer, UserEntity> map) {
        //UserEntity userEntity = null;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            BaseState state = map.get(i).getState();
            if (!(state instanceof DeadState) && !(state instanceof PoisonDeadState)) {
                list.add(map.get(i).getUserId()); //把不是死人的id添加到list里面
            }
        }
        //距离的映射，key为id，value为距离；
        Map<Integer, Integer> sortMap = new HashMap<>();
        for (Integer ids : list) {
            sortMap.put(ids, (integer - ids) * (integer - ids));
        }
        Set<Map.Entry<Integer, Integer>> entries = sortMap.entrySet();
        Object[] objects = entries.toArray();
        Arrays.sort(objects, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                Map.Entry<Integer, Integer> entry1 = (Map.Entry<Integer, Integer>) o1;
                Map.Entry<Integer, Integer> entry2 = (Map.Entry<Integer, Integer>) o2;
                return entry1.getValue() - entry2.getValue();
            }
        });
        Map.Entry<Integer, Integer> object = (Map.Entry<Integer, Integer>) objects[0];
        return players.get(object.getKey());
    }


    public Map<Integer, UserEntity> getPlayers() {
        return players;
    }

    public static int getWolfNum() {
        return wolfNum;
    }

    public static int getVillagerNum() {
        return villagerNum;
    }

    public static int getTellerNum() {
        return tellerNum;
    }

    public static int getWitchNum() {
        return witchNum;
    }

    public static int getHunterNum() {
        return hunterNum;
    }

    public static int getIdiotNum() {
        return idiotNum;
    }

    public static int getGuardNum() {
        return guardNum;
    }

    public Sequential getSequential() {
        return sequential;
    }

    public SpeechSequence getSpeechSequence() {
        return speechSequence;
    }

    public void setSpeechSequence(SpeechSequence speechSequence) {
        this.speechSequence = speechSequence;
    }

    public void setSequential(Sequential sequential) {
        this.sequential = sequential;
    }


    //构造方法里面应该传个state的
    public class StateAsyncTask extends AsyncTask<Void, Integer, Void> {
        private static final String TAG = "StateAsyncTask";
        public int stateDuration = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LogUtils.e(TAG, "onPreExecute", "preExecute");
            if (state instanceof DaytimeState) {
                if (dayNum == 1) {

                } else {
                    //若不是第一天,直接报夜
                    setState(new NotifyState());
                }
                dayNum++;
            }
            if (state instanceof WatchCardState) {
                //看牌的持续时间
                stateDuration = 5;
            }
            if (state instanceof NightState) {
                //天黑请闭眼持续时间
                stateDuration = 5;
            }
            if (state instanceof KillingState) {
                //狼人杀人讨论
                stateDuration = 5;
            }
            if (state instanceof WitchChooseState) {
                //女巫行动
                stateDuration = 5;
            }
            if (state instanceof VottingState) {
                //投票
                stateDuration = 5;
            }
            if (state instanceof TellerGetState) {
                //预言家验人阶段
                stateDuration = 5;
            }
            if (state instanceof HunterShootState) {
                //猎人杀人阶段
                stateDuration = 5;
            }
            if (state instanceof GuardProtectState) {
                //守卫守人阶段
                stateDuration = 5;
            }
            if (state instanceof ChiefCampaignState) {
                //上警环节
                stateDuration = 5;
            }
            if (state instanceof ChampaignVoteState) {
                //竞选警长的投票
                stateDuration = 5;
            }
            if (state instanceof HunterGetShootState) {
                stateDuration = 5;
            }
            if (state instanceof LastWordsState) {
                if (((LastWordsState) state).getId() == -1) {
                    stateDuration = 0;
                } else {
                    stateDuration = 30;

                }
            }

        }

        @Override
        protected Void doInBackground(Void... params) {
            LogUtils.e(TAG, "doInBackground", state + "do in background");
            for (int i = 0; i < stateDuration; i++) {
                if (isCancelled()) {
                    LogUtils.e(TAG, "onProgressUpdate", "truly cancelled");
                    return null;
                }
                try {
                    Thread.sleep(1000);
                    //更新进度
                    publishProgress((int) ((i * 1.0 / stateDuration) * 100));
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
            if (state.next() != null) {
                if (state instanceof NotifyState && dayNum == 1 && killedId != -1 && protectedId != killedId) {
                    //第一天被杀的话进入遗言阶段
                    setState(new LastWordsState(killedId));
                    return;
                }
                if (state instanceof LastWordsState && lastState instanceof NotifyState) {
                    //上一个阶段是报夜阶段的话
                    EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.STOP_SPEECH));
                    setState(new SpeechState());
                    return;
                }
                if (state instanceof LastWordsState) {
                    EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.STOP_SPEECH));
                    dead(((LastWordsState) state).getId());
                }
                if (state instanceof SpeechState) {
                    //发言阶段的切换不由这个异步任务控制

                }

                changeState();

            } else if (state instanceof ChampaignVoteState && stateDuration != 0) {
                synchronized (God.class) {
                    if (!hasVote) {
                        champaignVoteImpl();
                    } else {
                        hasVote = false;
                    }
                }
                setState(new NotifyState());
            } else if (state instanceof VottingState && stateDuration != 0) {
                synchronized (God.class) {
                    if (!hasVote) {
                        exileVoteImpl();
                    } else {
                        hasVote = false;
                    }
                }
                setState(new LastWordsState());
            } else if ((state instanceof LastWordsState && stateDuration != 0) || (stateDuration == 0 && state instanceof LastWordsState && ((LastWordsState) state).getId() == -1)) {
                setState(new NightState());
            }
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
            LogUtils.e(TAG, "onCancelled", "cancel task");

            if (state.next() != null) {
                if (state instanceof NotifyState && dayNum == 1 && killedId != -1 && protectedId != killedId) {
                    //第一天被杀的话进入遗言阶段
                    setState(new LastWordsState(killedId));
                    return;
                }
                if (state instanceof LastWordsState && lastState instanceof NotifyState) {
                    //上一个阶段是报夜阶段的话
                    EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.STOP_SPEECH));
                    setState(new SpeechState());
                    return;
                }
                if (state instanceof LastWordsState) {
                    EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.STOP_SPEECH));

                }
                if (state instanceof SpeechState) {
                    //发言阶段的切换不由这个异步任务控制

                }

                changeState();

            } else if (state instanceof ChampaignVoteState && stateDuration != 0) {
                synchronized (God.class) {
                    if (!hasVote) {
                        champaignVoteImpl();
                    } else {
                        hasVote = false;
                    }
                }

                setState(new NotifyState());
            } else if (state instanceof VottingState && stateDuration != 0) {
                synchronized (God.class) {
                    if (!hasVote) {
                        exileVoteImpl();
                    } else {
                        hasVote = false;
                    }
                }
                setState(new LastWordsState());
            } else if (state instanceof LastWordsState && stateDuration > 5) {
                setState(new NightState());
            }
        }
    }

    /**
     * 发言时的异步任务
     */
    public class SpeechAsyncTask extends AsyncTask<Void, Integer, Void> {
        private UserEntity userEntity;
        private final Map<Integer, UserEntity> map;
        private final boolean hasDead;
        int duration;

        public
        SpeechAsyncTask(UserEntity userEntity, Map<Integer, UserEntity> map, boolean hasDead) {
            this.userEntity = userEntity;
            this.map = map;
            this.hasDead = hasDead;
        }

        public UserEntity getUserEntity() {
            return userEntity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hasSpeechNum++;
            duration = 60;
            LogUtils.e(TAG, "onSpeechPreExecute", (userEntity.getUserId() + 1) + "号玩家开始发言");
            EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.SPEECH, userEntity.getUserId()));
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < duration; i++) {
                if (isCancelled()) {
                    LogUtils.e(TAG, "onSpeechProgressUpdate", "truly cancelled");
                    return null;
                }
                publishProgress(((int) ((i * 1.0 / duration) * 100)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (isCancelled()) {
                LogUtils.e(TAG, "onSpeechProgressUpdate", "truly cancelled");
                return;
            }
            LogUtils.e(TAG, "onSpeechProgressUpdate", values[0] + "");
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LogUtils.e(TAG, "onSpeechPostExecute", userEntity.getUserId() + "号玩家发言结束");
            EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.STOP_SPEECH));
            if (hasSpeechNum == map.size()) {
                hasSpeechNum = 0;
                LogUtils.e(TAG, "onSpeechPostExecute", "所有玩家发言结束");
                if (state.next() != null) {
                    changeState();
                } else {
                    if (map == chiefCampaignMap) {
                        setState(new ChampaignVoteState());
                    } else {
                        setState(new VottingState());
                    }
                }

            } else {
                if (sequential == Sequential.ANTI_CLOCKWISE) {
                    prevSpeech(userEntity, map, hasDead);
                } else {
                    nextSpeech(userEntity, map, hasDead);
                }
            }

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            LogUtils.e(TAG, "onSpeechCancelled", "cancel task");
            EventBus.getDefault().post(new JesusEventEntity(RoleType.ANY, JesusEvent.STOP_SPEECH));
            if (hasSpeechNum == map.size()) {
                hasSpeechNum = 0;
                LogUtils.e(TAG, "onSpeechPostExecute", "所有玩家发言结束");
                if (state.next() != null) {
                    changeState();
                } else {
                    if (map == chiefCampaignMap) {
                        setState(new ChampaignVoteState());
                    } else {
                        setState(new VottingState());
                    }
                }
            } else {
                if (sequential == Sequential.ANTI_CLOCKWISE) {
                    prevSpeech(userEntity, map, hasDead);
                } else {
                    nextSpeech(userEntity, map, hasDead);
                }
            }
        }
    }


}
