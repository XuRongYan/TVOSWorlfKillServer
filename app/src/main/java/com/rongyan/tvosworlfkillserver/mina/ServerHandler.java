package com.rongyan.tvosworlfkillserver.mina;

import com.rongyan.model.entity.UserEntity;
import com.rongyan.model.entity.UserEventEntity;
import com.rongyan.model.enums.RoleType;
import com.rongyan.model.message.ConfirmMessage;
import com.rongyan.tvosworlfkillserver.MessageEvent;
import com.rongyan.tvosworlfkillserver.activity.ConfigActivity;
import com.rongyant.commonlib.util.LogUtils;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import java.util.List;
import java.util.Random;

import de.greenrobot.event.EventBus;

import static com.rongyan.tvosworlfkillserver.MessageEvent.START_GAME_MESSAGE;

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
        String remoteIp = session.getRemoteAddress().toString().split(":")[0];

        MinaManager.userEntityMap.remove(remoteIp);
        MinaManager.liveUserMap.remove(remoteIp);
        MinaManager.sessionMap.remove(remoteIp);
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
            if (MinaManager.userEntityMap.containsKey(session.getRemoteAddress().toString().split(":")[0])) {
                session.write("该IP已被占用");
                return;
            }
            String remoteIp = session.getRemoteAddress().toString().split(":")[0];
            UserEntity userEntity = (UserEntity) message;
            //为userEntity赋值id
            userEntity.setUserId(MinaManager.userEntityMap.size());
            //获取牌堆
            List<RoleType> roleTypeList = ConfigActivity.roleTypeList;
            int randomNum = Math.abs(random.nextInt() % roleTypeList.size());
            //取随机值发牌
            userEntity.setRoleType(roleTypeList.get(randomNum));
            //userEntity.setRoleType(RoleType.WOLF);
            roleTypeList.remove(randomNum);
            //userEntity.setRoleType(RoleType.HUNTER);
            //添加索引
            MinaManager.userEntityMap.put(remoteIp, userEntity);
            MinaManager.liveUserMap.put(remoteIp, userEntity);
            MinaManager.sessionMap.put(remoteIp, session);
            session.write(userEntity);
            //TODO 测试的时候将开启游戏的条件定为一个就可以，一定记得改回去。。Integer.parseInt(ConfigActivity.selectedItem)
            if (MinaManager.userEntityMap.size() == Integer.parseInt(ConfigActivity.selectedItem)) {
                EventBus.getDefault().post(new MessageEvent(START_GAME_MESSAGE));
            }
            EventBus.getDefault().post(new MessageEvent(CONNECTED_PLAYER_UPDATED));
        }
        if (message instanceof UserEventEntity) {
            EventBus.getDefault().post(message);
        }
        if (message instanceof ConfirmMessage) {
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
