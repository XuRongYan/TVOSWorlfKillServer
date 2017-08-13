package com.rongyan.tvosworlfkillserver.mina;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

/**
 * 心跳包类
 * Created by XRY on 2017/8/1.
 */

public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {
    public static final String HEART_BEAT_REQUEST = "heart beat request";
    public static final String HEART_BEAT_RESPONSE = "heart beat response";

    private static final String TAG = "KeepAliveMessageFactory";

    @Override
    public boolean isRequest(IoSession ioSession, Object o) {
        if (o.equals(HEART_BEAT_REQUEST)) {
            //LogUtils.e(TAG, "isRequest", "请求心跳包信息：" + o);
            return true;
        }
        return false;
    }

    @Override
    public boolean isResponse(IoSession ioSession, Object o) {
        if (o.equals(HEART_BEAT_RESPONSE)) {
            //LogUtils.e(TAG, "isResponse", "响应心跳包信息：" + o);
            return true;
        }
        return false;
    }

    @Override
    public Object getRequest(IoSession ioSession) {
        //LogUtils.e(TAG, "getRequest", "请求预设信息:" + HEART_BEAT_REQUEST);
        return HEART_BEAT_REQUEST;
    }

    @Override
    public Object getResponse(IoSession ioSession, Object o) {
        //LogUtils.e(TAG, "getResponse", "响应预设信息：" + HEART_BEAT_RESPONSE);
        return HEART_BEAT_RESPONSE;
    }
}
