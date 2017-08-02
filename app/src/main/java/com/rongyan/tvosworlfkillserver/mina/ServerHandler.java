package com.rongyan.tvosworlfkillserver.mina;

import com.rongyan.model.entity.UserEntity;
import com.rongyan.tvosworlfkillserver.MessageEvent;
import com.rongyant.commonlib.util.LogUtils;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/1.
 */

public class ServerHandler extends IoHandlerAdapter {
    private static final String TAG = "ServerHandler";
    public static final String CONNECTED_PLAYER_UPDATED = "ip map updated";

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
        LogUtils.e(TAG, "sessionCreated", "ip:" + session.getRemoteAddress().toString()
                + " session created");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        LogUtils.e(TAG, "sessionOpened", "ip:" + session.getRemoteAddress().toString()
                + " session opened");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        LogUtils.e(TAG, "sessionClosed", "ip:" + session.getRemoteAddress().toString()
                + " session closed");
        MinaManager.liveUserMap.remove(session.getRemoteAddress().toString());
        EventBus.getDefault().post(new MessageEvent(CONNECTED_PLAYER_UPDATED));
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
        LogUtils.e(TAG, "sessionIdle", "ip:" + session.getRemoteAddress().toString()
                + " session idled");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        LogUtils.e(TAG, "messageReceived", "ip:" + session.getRemoteAddress().toString()
                + " received" + message);
        if (message instanceof UserEntity) {
            UserEntity userEntity = (UserEntity) message;
            userEntity.setUserId(MinaManager.userEntityMap.size());
            MinaManager.userEntityMap.put(session.getRemoteAddress().toString(), userEntity);
            MinaManager.liveUserMap.put(session.getRemoteAddress().toString(), userEntity);
            session.write(userEntity);
            if (MinaManager.userEntityMap.size() == 12) {
                //通知LauncherAty切换界面选择游戏板子

            }
            EventBus.getDefault().post(new MessageEvent(CONNECTED_PLAYER_UPDATED));
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        LogUtils.e(TAG, "messageSent", "ip:" + session.getRemoteAddress().toString()
                + " sent" + message);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        LogUtils.e(TAG, "exceptionCaught", cause.toString());
        cause.printStackTrace();
    }
}
