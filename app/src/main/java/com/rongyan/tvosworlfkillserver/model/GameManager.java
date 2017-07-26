package com.rongyan.tvosworlfkillserver.model;

import android.util.ArrayMap;

import com.rongyan.tvosworlfkillserver.model.abstractinterface.Role;
import com.rongyan.tvosworlfkillserver.model.entity.GuardEntity;
import com.rongyan.tvosworlfkillserver.model.entity.HunterEntity;
import com.rongyan.tvosworlfkillserver.model.entity.IdiotEntity;
import com.rongyan.tvosworlfkillserver.model.entity.TellerEntity;
import com.rongyan.tvosworlfkillserver.model.entity.VillagerEntity;
import com.rongyan.tvosworlfkillserver.model.entity.WitchEntity;
import com.rongyan.tvosworlfkillserver.model.entity.WolfEntity;
import com.rongyan.tvosworlfkillserver.model.enums.DayTime;
import com.rongyan.tvosworlfkillserver.model.enums.Sequential;
import com.rongyan.tvosworlfkillserver.model.enums.VoteStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.R.attr.id;

/**
 * 游戏管理类
 * Created by XRY on 2017/7/26.
 */

public class GameManager {
    private static Map<Integer, Role> players = new ArrayMap<>();
    private static GameManager INSTANCE = null;
    private static int VILLAGER_NUM = 0; //村民数量
    private static int WOLF_NUM = 0; //狼人数量
    private static int TELLER_NUM = 0; //预言家数量
    private static int WITCH_NUM = 0; //女巫数量
    private static int HUNTER_NUM = 0; //猎人数量
    private static int IDIOT_NUM = 0; //白痴数量
    private static int GUARD_NUM = 0; //守卫数量
    private int maxVoteNum = 0; //被投最多的票数
    private int progress = 0; //游戏进程（第几天）
    private Sequential sequential = Sequential.CLOCKWISE; //发言顺序
    private DayTime dayTimeStatus = null; //白天或者黑夜

    //角色Id的索引，用于睁眼
    private static List<Integer> villagerIds = new ArrayList<>();
    private static List<Integer> wolfIds = new ArrayList<>();
    private static int tellerId = -1;
    private static int witchId = -1;
    private static int hunterId = -1;
    private static int idiotId = -1;
    private static int guardId = -1;


    private static GameManager getInstance() {
        if (INSTANCE == null) {
            synchronized (GameManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GameManager();
                }
            }
        }
        return INSTANCE;
    }

    private GameManager() {
    }

    public void setPlayers(Map<Integer, Role> players) {
        this.players = players;
    }

    /**
     * 检查投票的情况，并作出相应的反应
     */
    public VoteStatus checkVoted() {
        Collection<Role> values = players.values();
        Iterator<Role> iterator = values.iterator();
        List<Role> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        Collections.sort(list, new Comparator<Role>() {
            @Override
            public int compare(Role o1, Role o2) {
                if (o1.getVotedNumber() > o2.getVotedNumber()) {
                    return 1;
                } else if (o1.getVotedNumber() < o2.getVotedNumber()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        maxVoteNum = list.get(list.size() - 1).getVotedNumber();
        if (list.get(list.size() - 1).getVotedNumber() != list.get(list.size() - 2).getVotedNumber()) {
            //没有平票
            return VoteStatus.NOT_DRAW;
        } else {
            //平票了
            return VoteStatus.DRAW;
        }
    }

    /**
     * 获取平票PK的玩家
     *
     * @param maxVoteNum
     * @return
     */
    private Map<Integer, Role> getPkMap(int maxVoteNum) {
        Map<Integer, Role> pkPlayers = new LinkedHashMap<>();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getVotedNumber() == maxVoteNum) {
                pkPlayers.put(pkPlayers.size(), players.get(i));
            }
        }
        return pkPlayers;
    }

    /**
     * 执行放逐投票
     *
     * @param id
     */
    private void voteExecute(int id) {
        Role role = players.get(id);
        if (!(role instanceof IdiotEntity && ((IdiotEntity) role).isFirstVoted())) {
            role.getPrev().setNext(role.getNext());
            role.getNext().setPrev(role.getPrev());
            role.setNext(null);
            role.setPrev(null);
            initDieStatus(id);
            role.setVoteDie(true);
            players.remove(id);
        } else {
            ((IdiotEntity) role).setFirstVoted(false);
        }


    }

    /**
     * 初始化死法
     *
     * @param id
     */
    private void initDieStatus(int id) {
        Role role = players.get(id);
        role.setVoteDie(false);
        role.setKillDie(false);
        role.setPoisonDie(false);
        role.setShootDie(false);

    }

    /**
     * 请某个玩家发言
     *
     * @param id
     */
    public void speech(int id) {
        players.get(id).setSpeeching(true);
    }

    /**
     * 结束发言
     *
     * @param id
     */
    public void endSpeech(int id) {
        initSpeeching();

    }

    /**
     * 自动下一个发言
     */
    public void nextSpeech() {
        if (sequential == Sequential.CLOCKWISE) {
            speech(players.get(id).getNext().getId()); //下一个玩家发言
        } else {
            speech(players.get(id).getPrev().getId()); //下一个玩家发言
        }
    }

    /**
     * 特殊情况下指定下一个发言
     *
     * @param id
     */
    public void nextSpeech(int id) {
        speech(id);
    }

    /**
     * 初始化发言状态
     */
    private void initSpeeching() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setSpeeching(false);
        }
    }

    public static List<Integer> getVillagerIds() {
        return villagerIds;
    }

    public static void setVillagerIds(List<Integer> villagerIds) {
        GameManager.villagerIds = villagerIds;
    }

    public static List<Integer> getWolfIds() {
        return wolfIds;
    }

    public static void setWolfIds(List<Integer> wolfIds) {
        GameManager.wolfIds = wolfIds;
    }

    public static int getTellerId() {
        return tellerId;
    }

    public static void setTellerId(int tellerId) {
        GameManager.tellerId = tellerId;
    }

    public static int getWitchId() {
        return witchId;
    }

    public static void setWitchId(int witchId) {
        GameManager.witchId = witchId;
    }

    public static int getHunterId() {
        return hunterId;
    }

    public static void setHunterId(int hunterId) {
        GameManager.hunterId = hunterId;
    }

    public static int getIdiotId() {
        return idiotId;
    }

    public static void setIdiotId(int idiotId) {
        GameManager.idiotId = idiotId;
    }

    public static int getGuardId() {
        return guardId;
    }

    public static void setGuardId(int guardId) {
        GameManager.guardId = guardId;
    }

    public Sequential getSequential() {
        return sequential;
    }

    public void setSequential(Sequential sequential) {
        this.sequential = sequential;
    }

    public static class GameBuilder {
        private int number = 12; //人数
        private int tempNum;

        public GameBuilder(int number) {
            this.number = number;
            tempNum = number;

        }

        public GameBuilder setNumber(int number) {
            this.number = number;
            tempNum = number;
            return this;
        }

        public GameBuilder setVillager(int number) {
            VILLAGER_NUM = number;
            tempNum -= number;
            return this;
        }

        public GameBuilder setWorlf(int number) {
            WOLF_NUM = number;
            tempNum -= number;
            return this;
        }

        public GameBuilder setTeller() {
            TELLER_NUM = 1;
            tempNum--;
            return this;
        }

        public GameBuilder setWitch() {
            WITCH_NUM = 1;
            tempNum--;
            return this;
        }

        public GameBuilder setHunter() {
            HUNTER_NUM = 1;
            tempNum--;
            return this;
        }

        public GameBuilder setGuard() {
            GUARD_NUM = 1;
            tempNum--;
            return this;
        }

        public GameBuilder setIdiot() {
            IDIOT_NUM = 1;
            tempNum--;
            return this;
        }

        public GameManager build() throws NumberIsNotEnoughException, NumberOutOfExpectedException {
            isNumOutOfExpected();
            isNumberNotEnough();
            Random random = new Random();
            List<Role> list = new ArrayList<>();
            for (int i = 0; i < VILLAGER_NUM; i++) {
                list.add(new VillagerEntity(players));
            }

            for (int i = 0; i < WOLF_NUM; i++) {
                list.add(new WolfEntity(players));
            }

            for (int i = 0; i < TELLER_NUM; i++) {
                list.add(new TellerEntity(players));
            }

            for (int i = 0; i < WITCH_NUM; i++) {
                list.add(new WitchEntity(players));
            }

            for (int i = 0; i < HUNTER_NUM; i++) {
                list.add(new HunterEntity(players));
            }

            for (int i = 0; i < GUARD_NUM; i++) {
                list.add(new GuardEntity(players));
            }

            for (int i = 0; i < IDIOT_NUM; i++) {
                list.add(new IdiotEntity(players));
            }

            for (int i = 0; i < number; i++) {
                int index = random.nextInt() % (12 - i);
                Role role = list.get(index);
                role.setId(i);
                role.setManagerIndex();
                players.put(i, role);
                list.remove(index);
            }

            //为节点的next,prev赋值
            for (int i = 0; i < number; i++) {
                players.get(i).setNext(players.get((i + 1) % number));
                players.get(i).setPrev(players.get((number - 1 + i) % number));
            }


            return GameManager.getInstance();

        }

        private void isNumOutOfExpected() throws NumberOutOfExpectedException {
            if (tempNum < 0) {
                throw new NumberOutOfExpectedException("Number out of expected,expected:"
                        + number + ",but now:"
                        + (number - tempNum));
            }
        }

        private void isNumberNotEnough() throws NumberIsNotEnoughException {
            if (tempNum > 0) {
                throw new NumberIsNotEnoughException("Number isn't Enough,expected:"
                        + number + ",but only:"
                        + (number - tempNum));
            }
        }
    }

    public static class NumberOutOfExpectedException extends Exception {
        public NumberOutOfExpectedException(String message) {
            super(message);
        }
    }

    public static class NumberIsNotEnoughException extends Exception {
        public NumberIsNotEnoughException(String message) {
            super(message);
        }
    }
}
