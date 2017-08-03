package com.rongyan.tvosworlfkillserver.mina;

import com.rongyan.model.entity.UserEntity;
import com.rongyan.model.entity.UserEventEntity;
import com.rongyan.model.enums.RoleType;
import com.rongyan.tvosworlfkillserver.MessageEvent;
import com.rongyan.tvosworlfkillserver.activity.ConfigActivity;
import com.rongyant.commonlib.util.LogUtils;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;

/**
 * Created by XRY on 2017/8/1.
 */

public class ServerHandler extends IoHandlerAdapter {
    private static final String TAG = "ServerHandler";
    public static final String CONNECTED_PLAYER_UPDATED = "ip map updated";
    private Random random = new Random();

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
            if (MinaManager.userEntityMap.containsKey(session.getRemoteAddress().toString())) {
                return;
            }
            UserEntity userEntity = (UserEntity) message;
            //为userEntity赋值id
            userEntity.setUserId(MinaManager.userEntityMap.size());
            //获取牌堆
            List<RoleType> roleTypeList = ConfigActivity.roleTypeList;
            int randomNum = Math.abs(random.nextInt() % roleTypeList.size());
            //取随机值发牌
            userEntity.setRoleType(roleTypeList.get(randomNum));
            //添加索引
            MinaManager.userEntityMap.put(session.getRemoteAddress().toString(), userEntity);
            MinaManager.liveUserMap.put(session.getRemoteAddress().toString(), userEntity);
            MinaManager.sessionMap.put(session.getRemoteAddress().toString(), session);
            session.write(userEntity);
            if (MinaManager.userEntityMap.size() == MinaManager.userEntityMap.size()) {

            }
            EventBus.getDefault().post(new MessageEvent(CONNECTED_PLAYER_UPDATED));
        }
        if (message instanceof UserEventEntity) {
            EventBus.getDefault().post(message);
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
