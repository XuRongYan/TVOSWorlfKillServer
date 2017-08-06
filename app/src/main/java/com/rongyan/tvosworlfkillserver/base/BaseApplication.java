package com.rongyan.tvosworlfkillserver.base;

import android.app.Application;

import com.rongyan.tvosworlfkillserver.GodProxy;
import com.rongyan.tvosworlfkillserver.mina.MinaManager;

/**
 *
 * Created by XRY on 2017/4/14.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MinaManager instance = MinaManager.getInstance();
        instance.initServer();
        GodProxy.getInstance();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MinaManager.getInstance().shutDownServer();
    }
}
