package com.rongyan.tvosworlfkillserver;

import com.rongyan.model.entity.JesusEventEntity;
import com.rongyan.model.entity.UserEventEntity;
import com.rongyan.tvosworlfkillserver.mina.MinaManager;

import org.apache.mina.core.session.IoSession;

import java.util.Collection;
import java.util.Iterator;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by XRY on 2017/8/2.
 */

public class GodProxy {
    private static GodProxy INSTANCE = null;
    Collection<IoSession> values;
    private GodProxy() {
        EventBus.getDefault().register(this);
        values = MinaManager.sessionMap.values();
    }

    public static GodProxy getInstance() {
        if (INSTANCE == null) {
            synchronized (GodProxy.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GodProxy();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 代理收到上帝发来的消息就把消息转发给所有连接到的主机
     * @param jesusEventEntity
     */
    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onMessageEvent(JesusEventEntity jesusEventEntity) {
        Iterator<IoSession> iterator = values.iterator();
        while (iterator.hasNext()) {
            IoSession next = iterator.next();
            next.write(jesusEventEntity);
        }
    }

    /**
     * 代理收到用户发来的消息转发给上帝
     * @param userEventEntity
     */
    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void onMessageEvent(UserEventEntity userEventEntity) {
        EventBus.getDefault().post(userEventEntity);
    }

    public void unregister() {
        EventBus.getDefault().unregister(this);
    }

}
