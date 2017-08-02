package com.rongyan.tvosworlfkillserver;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rongyan.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.base.BaseActivity;
import com.rongyan.tvosworlfkillserver.entity.GodCheck;
import com.rongyan.tvosworlfkillserver.exceptions.PlayerNotFitException;
import com.rongyan.tvosworlfkillserver.mina.MinaManager;
import com.rongyant.commonlib.util.LogUtils;
import com.rongyant.commonlib.util.NetWorkUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class LaunchActivity extends BaseActivity {
    private static final String TAG = "LaunchActivity";
    @BindView(R.id.tv_server_ip)
    TextView tvServerIp;
    @BindView(R.id.tv_connected_player)
    TextView tvConnectedPeople;

    private boolean hasTeller = false;
    private boolean hasWitch = false;
    private boolean hasHunter = false;
    private boolean hasIdiot = false;
    private boolean hasGuard = false;
    private int wolfNum = -1;
    private int villagerNum = -1;
    private God god;

    @Override
    protected int getContentView() {
        return R.layout.activity_launch;
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

    @Override
    protected void initViews(Bundle savedInstanceState) {
        wolfNum = getIntent().getIntExtra("wolf", -1);
        villagerNum = getIntent().getIntExtra("villager", -1);
        tvServerIp.setText("服务器IP:" + NetWorkUtil.getHostIp());
        tvConnectedPeople.setText("当前连接人数：" + MinaManager.liveUserMap.size());
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

    private void initGame() {
        Map<Integer, UserEntity> userEntityMap = new LinkedHashMap<>();
        Collection<UserEntity> values = MinaManager.userEntityMap.values();
        Iterator<UserEntity> iterator = values.iterator();
        while (iterator.hasNext()) {
            UserEntity next = iterator.next();
            userEntityMap.put(next.getUserId(), next);
        }
        God.Builder builder = new God.Builder(userEntityMap)
                .setVillagers(villagerNum)
                .setWolves(wolfNum);
        if (hasTeller) {
            builder.setTeller();
        }
        if (hasWitch) {
            builder.setWitch();
        }
        if (hasGuard) {
            builder.setGuard();
        }
        if (hasIdiot) {
            builder.setIdiot();
        }
        if (hasHunter) {
            builder.setHunter();
        }
        try {
            god = builder.build();
        } catch (PlayerNotFitException e) {
            e.printStackTrace();
        }

    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        LogUtils.e(TAG, "onMessageEvent", event.toString());
        tvConnectedPeople.setText("当前连接人数：" + MinaManager.liveUserMap.size());
    }

    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true)
    public void onMessageEvent(List<GodCheck> event) {
        LogUtils.e(TAG, "onMessageEvent", event.toString());
        for (GodCheck godCheck :
                event) {
            String godName = godCheck.getGodName();
            if (godName.equals(getString(R.string.string_teller))) {
                hasTeller = true;
            }
            if (godName.equals(getString(R.string.string_witcher))) {
                hasWitch = true;
            }
            if (godName.equals(getString(R.string.string_hunter))) {
                hasHunter = true;
            }
            if (godName.equals(getString(R.string.string_idiot))) {
                hasIdiot = true;
            }
            if (godName.equals(getString(R.string.string_guard))) {
                hasGuard = true;
            }
        }
    }
}
