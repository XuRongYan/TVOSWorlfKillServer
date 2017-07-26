package com.rongyan.tvosworlfkillserver.Model;

import android.util.ArrayMap;

import com.rongyan.tvosworlfkillserver.Model.entity.GuardEntity;
import com.rongyan.tvosworlfkillserver.Model.entity.HunterEntity;
import com.rongyan.tvosworlfkillserver.Model.entity.IdiotEntity;
import com.rongyan.tvosworlfkillserver.Model.entity.TellerEntity;
import com.rongyan.tvosworlfkillserver.Model.entity.VillagerEntity;
import com.rongyan.tvosworlfkillserver.Model.entity.WitchEntity;
import com.rongyan.tvosworlfkillserver.Model.entity.WolfEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by XRY on 2017/7/26.
 */

public class GameManager {
    private static Map<Integer, Role> players = new ArrayMap<>();
    private static GameManager INSTANCE = null;
    public static int VILLAGER_NUM = 0;
    public static int WOLF_NUM = 0;
    public static int TELLER_NUM = 0;
    public static int WITCH_NUM = 0;
    public static int HUNTER_NUM = 0;
    public static int IDIOT_NUM = 0;
    public static int GUARD_NUM = 0;

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
    public VoteStatus checkVotedAndExecute() {
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

        if (list.get(list.size() - 1).getVotedNumber() != list.get(list.size() - 2).getVotedNumber()) {
            //没有平票
            return VoteStatus.NOT_DRAW;
        } else {
            //平票了遍历找出平票的
            return VoteStatus.DRAW;
        }
    }

    private void voteExecute(int id) {
        initDieStatus(id);
        Role role = players.get(id);
        role.setVoteDie(true);
        players.remove(id);

    }

    private void initDieStatus(int id) {
        Role role = players.get(id);
        role.setVoteDie(false);
        role.setKillDie(false);
        role.setPoisonDie(false);
        role.setShootDie(false);

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
                players.put(i, role);
                list.remove(index);
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

    private static class NumberOutOfExpectedException extends Exception {
        public NumberOutOfExpectedException(String message) {
            super(message);
        }
    }

    private static class NumberIsNotEnoughException extends Exception {
        public NumberIsNotEnoughException(String message) {
            super(message);
        }
    }
}
