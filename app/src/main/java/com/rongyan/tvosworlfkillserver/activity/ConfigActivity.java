package com.rongyan.tvosworlfkillserver.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rongyan.model.enums.RoleType;
import com.rongyan.tvosworlfkillserver.LaunchActivity;
import com.rongyan.tvosworlfkillserver.R;
import com.rongyan.tvosworlfkillserver.adapter.CheckGodAdapter;
import com.rongyan.tvosworlfkillserver.base.BaseActivity;
import com.rongyan.tvosworlfkillserver.entity.GodCheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class ConfigActivity extends BaseActivity {
    @BindView(R.id.tv_game_mode)
    TextView tvGameMode;
    @BindView(R.id.spinner_player_num)
    Spinner spinnerPlayerNum;
    @BindView(R.id.tv_villager_num)
    TextView tvVillagerNum;
    @BindView(R.id.tv_wolf_num)
    TextView tvWolfNum;
    @BindView(R.id.recycler_god)
    RecyclerView recyclerGod;
    @BindView(R.id.btn_config_next)
    Button btnConfigNext;

    List<RoleType> list = new ArrayList<>();
    public static List<RoleType> roleTypeList;

    private GodCheck[] godChecks;
    private int wolfNum = 4;
    private int villagerNum = 4;
    private CheckGodAdapter checkGodAdapter;
    public static String selectedItem;

    @Override
    protected int getContentView() {
        return R.layout.activity_config;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        godChecks = new GodCheck[]{new GodCheck(getString(R.string.string_teller), true),
                new GodCheck(getString(R.string.string_witcher), true),
                new GodCheck(getString(R.string.string_hunter), true),
                new GodCheck(getString(R.string.string_idiot), true),
                new GodCheck(getString(R.string.string_guard), false)};
        spinnerPlayerNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = (String) spinnerPlayerNum.getSelectedItem();
                switch (Integer.parseInt(selectedItem)) {
                    case 12:
                        tvGameMode.setText(getString(R.string.string_kill_side));
                        wolfNum = 4;
                        villagerNum = 4;

                        break;
                    case 9:
                        tvGameMode.setText(getString(R.string.string_kill_side));
                        villagerNum = 3;
                        wolfNum = 3;
                        break;
                    case 6:
                        tvGameMode.setText(getString(R.string.string_kill_all));
                        villagerNum = 2;
                        wolfNum = 2;
                        break;
                }
                tvWolfNum.setText("狼人人数：" + wolfNum);
                tvVillagerNum.setText("村民人数：" + villagerNum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initRecyclerView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initRecyclerView() {
        checkGodAdapter = new CheckGodAdapter(this, Arrays.asList(godChecks), recyclerGod);
        recyclerGod.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerGod.setAdapter(checkGodAdapter);
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

    @OnClick({R.id.btn_config_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_config_next:
                checkNum();
                break;
        }
    }

    private void checkNum() {
        roleTypeList = new ArrayList<>();
        int godNum = 0;
        for (int i = 0; i < checkGodAdapter.getList().size(); i++) {
            GodCheck godCheck = checkGodAdapter.getList().get(i);
            if (godCheck.isCheck()) {
                godNum++;
                String godName = godCheck.getGodName();
                if (godName.equals(getString(R.string.string_teller))) {
                    roleTypeList.add(RoleType.TELLER);
                }
                if (godName.equals(getString(R.string.string_witcher))) {
                    roleTypeList.add(RoleType.WITCH);
                }
                if (godName.equals(getString(R.string.string_hunter))) {
                    roleTypeList.add(RoleType.HUNTER);
                }
                if (godName.equals(getString(R.string.string_idiot))) {
                    roleTypeList.add(RoleType.IDIOT);
                }
                if (godName.equals(getString(R.string.string_guard))) {
                    roleTypeList.add(RoleType.GUARD);
                }
            }
        }
        for (int i = 0; i < villagerNum; i++) {
            roleTypeList.add(RoleType.VILLAGER);
        }
        for (int i = 0; i < wolfNum; i++) {
            roleTypeList.add(RoleType.WOLF);
        }
        if (godNum > villagerNum) {
            Toast.makeText(mContext, getString(R.string.god_num_too_more), Toast.LENGTH_SHORT).show();
        } else if (godNum < villagerNum) {
            Toast.makeText(mContext, getString(R.string.god_not_enough), Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("wolf", wolfNum);
            bundle.putInt("villager", villagerNum);

            goActivity(LaunchActivity.class, bundle);
        }
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMessageEvent(String string) {
        if (string.equals("LaunchActivity start")) {
            EventBus.getDefault().post(checkGodAdapter.getList());
        }
    }


}
