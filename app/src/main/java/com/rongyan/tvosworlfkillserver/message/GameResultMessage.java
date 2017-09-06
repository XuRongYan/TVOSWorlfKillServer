package com.rongyan.tvosworlfkillserver.message;

import com.rongyan.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.enums.GameResult;

import java.util.Map;

/**
 * Created by XRY on 2017/8/17.
 */

public class GameResultMessage {
    private GameResult result;
    private Map<Integer, UserEntity> players;

    public GameResultMessage(GameResult result, Map<Integer, UserEntity> players) {
        this.result = result;
        this.players = players;
    }

    public GameResult getResult() {
        return result;
    }

    public void setResult(GameResult result) {
        this.result = result;
    }

    public Map<Integer, UserEntity> getPlayers() {
        return players;
    }

    public void setPlayers(Map<Integer, UserEntity> players) {
        this.players = players;
    }
}
