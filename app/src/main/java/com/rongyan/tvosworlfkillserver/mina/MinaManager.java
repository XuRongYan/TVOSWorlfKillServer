package com.rongyan.tvosworlfkillserver.mina;

import android.util.ArrayMap;

import com.rongyan.model.entity.UserEntity;
import com.rongyant.commonlib.util.LogUtils;
import com.rongyant.commonlib.util.NetWorkUtil;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

/**
 * Created by XRY on 2017/8/1.
 */

public class MinaManager {
    private static final String TAG = "MinaManager";
    //ip地址与用户实体对应的map
    public static Map<String, UserEntity> userEntityMap = new ArrayMap<>();
    //30S后超时
    public static final int IDLE_TIMEOUT = 30;
    //15s发送一次心跳包
    public static final int HEART_BEAT_RATE = 15;

    public static final int PORT = 9800;

    private static MinaManager INSTANCE = null;

    private MinaManager() {
        //initServer();
    }

    public static MinaManager getInstance() {
        if (INSTANCE == null) {
            synchronized (MinaManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MinaManager();
                }
            }
        }
        return INSTANCE;
    }

    public void initServer() {
        SocketAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDLE_TIMEOUT);
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl(); //心跳包收发处理
        KeepAliveRequestTimeoutHandler heartBeatTimeoutHandler = new KeepAliveRequestTimeoutHandlerImpl(); //心跳包超时处理
        KeepAliveFilter heartBeatFilter = new KeepAliveFilter(heartBeatFactory, IdleStatus.BOTH_IDLE, heartBeatTimeoutHandler);
        heartBeatFilter.setForwardEvent(true); //向后传递时间（默认截取空闲事件，这里设置成可以向后传递）
        heartBeatFilter.setRequestInterval(HEART_BEAT_RATE); //设置心跳包频率
        acceptor.getFilterChain().addLast("heartbeat", heartBeatFilter);
        acceptor.setHandler(new ServerHandler());
        try {
            acceptor.bind(new InetSocketAddress(NetWorkUtil.getHostIp(), PORT));
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.e(TAG, "initServer", "server start on:" + NetWorkUtil.getHostIp() + ":" + PORT);

    }
}
