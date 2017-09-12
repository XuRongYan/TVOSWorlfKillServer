package com.rongyan.tvosworlfkillserver;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rongyan.model.entity.UserEntity;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.message.ToastMessage;
import com.rongyan.tvosworlfkillserver.activity.ConfigActivity;
import com.rongyan.tvosworlfkillserver.adapter.PlayerAdapter;
import com.rongyan.tvosworlfkillserver.base.BaseActivity;
import com.rongyan.tvosworlfkillserver.base.BaseAppManager;
import com.rongyan.tvosworlfkillserver.enums.GameResult;
import com.rongyan.tvosworlfkillserver.message.GameResultMessage;
import com.rongyan.tvosworlfkillserver.mina.MinaManager;
import com.rongyan.tvosworlfkillserver.popupwindowHelper.PopupWindowUtil;
import com.rongyant.commonlib.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private List<UserEntity> userEntities = new ArrayList<>();
    private PlayerAdapter adapter;
    private God god;
    private PopupWindowUtil showEndGame;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        god = GodHolder.god;
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

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEvent(ToastMessage message) {
        Toast.makeText(mContext, message.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEvent(GameResultMessage event) {
        StringBuilder wolves = new StringBuilder();
        StringBuilder villagers = new StringBuilder();
        StringBuilder gods = new StringBuilder();
        Map<Integer, UserEntity> map = event.getPlayers();

        Collection<UserEntity> values = map.values();
        for (UserEntity value : values) {
            if (value.getRoleType() == RoleType.WOLF) {
                if (wolves.toString().equals("")) {
                    wolves.append(value.getUserId()).append("号");
                } else {
                    wolves.append(",").append(value.getUserId()).append("号");
                }
                continue;
            }

            if (value.getRoleType() == RoleType.VILLAGER) {
                if (villagers.toString().equals("")) {
                    villagers.append(value.getUserId()).append("号");
                } else {
                    villagers.append(",").append(value.getUserId()).append("号");
                }
                continue;
            }
            String strRoleType = null;
            RoleType roleType = value.getRoleType();
            switch (roleType) {
                case TELLER:
                    strRoleType = "预言家";
                    break;
                case WITCH:
                    strRoleType = "女巫";
                    break;
                case HUNTER:
                    strRoleType = "猎人";
                    break;
                case GUARD:
                    strRoleType = "守卫";
                    break;
                case IDIOT:
                    strRoleType = "白痴";
                    break;
            }
            if (gods.toString().equals("")) {
                gods.append(value.getUserId()).append("号(").append(strRoleType != null ? strRoleType : "").append(")");
            } else {
                villagers.append(",").append(value.getUserId()).append("号(").append(strRoleType != null ? strRoleType : "").append(")");
            }

        }
        showEndGame = new PopupWindowUtil.Builder(this)
                .setView(R.layout.popup_ganme_end)
                .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                .setText(R.id.tv_popup_game_end_wolf, wolves.toString())
                .setText(R.id.tv_popup_game_end_villager, villagers.toString())
                .setText(R.id.tv_popup_game_end_god, gods.toString())
                .setText(R.id.tv_popup_game_end_result, (event.getResult() == GameResult.WOLF_WIN ? "狼人" : "好人") + "阵营胜利")
                .setOnClickListener(R.id.btn_exit_game, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BaseAppManager.getInstance().clearAll();
                        System.exit(0);
                    }
                })
                .setOnClickListener(R.id.btn_start_game, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GodHolder.god.releaseGod();
                        MinaManager.getInstance().closeAllSession();
                        goActivity(ConfigActivity.class);
                    }
                })
                .build();

        showEndGame.show(getLayoutInflater().inflate(getContentView(), null), Gravity.NO_GRAVITY, 0, 0);

    }

    private void initUserEntities() {
        Map<Integer, UserEntity> players = null;
        if (god != null) {
            players = god.getPlayers();
        } else {
            LogUtils.e(TAG, "initViews", "god is null");
            finish();
        }
        for (int i = 0; i < players.size(); i++) {
            userEntities.add(players.get(i));
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        PlayerAdapter adapter = new PlayerAdapter(this, userEntities, recyclerView);
        recyclerView.setAdapter(adapter);
    }
}
