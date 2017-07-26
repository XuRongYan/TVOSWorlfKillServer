package com.rongyan.tvosworlfkillserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rongyan.tvosworlfkillserver.model.GameManager;

public class MainActivity extends AppCompatActivity {

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            gameManager = new GameManager.GameBuilder(12)
                    .setVillager(4)
                    .setWorlf(4)
                    .setTeller()
                    .setWitch()
                    .setHunter()
                    .setIdiot()
                    .build();
        } catch (GameManager.NumberIsNotEnoughException | GameManager.NumberOutOfExpectedException e) {
            e.printStackTrace();
        }

        gameManager.speech(5);
        gameManager.endSpeech(5);

    }


}
