package com.rongyan.tvosworlfkillserver;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.rongyan.tvosworlfkillserver.base.BaseActivity;
import com.rongyan.tvosworlfkillserver.mina.MinaManager;
import com.rongyant.commonlib.util.LogUtils;
import com.rongyant.commonlib.util.NetWorkUtil;

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
        tvServerIp.setText("服务器IP:" + NetWorkUtil.getHostIp());
        tvConnectedPeople.setText("当前连接人数：" + MinaManager.userEntityMap.size());
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

    @Subscribe(threadMode = ThreadMode.MainThread,sticky = true)
    public void onMessageEvent(MessageEvent event) {
        LogUtils.e(TAG, "onMessageEvent", event.toString());
        tvConnectedPeople.setText("当前连接人数：" + MinaManager.userEntityMap.size());
    }
}
