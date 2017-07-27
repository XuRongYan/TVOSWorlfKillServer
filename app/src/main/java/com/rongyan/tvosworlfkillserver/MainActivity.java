package com.rongyan.tvosworlfkillserver;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.rongyan.tvosworlfkillserver.adapter.PlayerAdapter;
import com.rongyan.tvosworlfkillserver.base.BaseActivity;
import com.rongyan.tvosworlfkillserver.model.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private GameManager gameManager;
    private List<UserEntity> userEntities = new ArrayList<>();
    private PlayerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        initUserEntities();
        initRecyclerView();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void initUserEntities() {
        for (int i = 0; i < 12; i++) {
            UserEntity entity = new UserEntity(i, "test" + i, R.mipmap.ic_launcher);
            userEntities.add(entity);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        PlayerAdapter adapter = new PlayerAdapter(this, userEntities, recyclerView);
        recyclerView.setAdapter(adapter);
    }


}
