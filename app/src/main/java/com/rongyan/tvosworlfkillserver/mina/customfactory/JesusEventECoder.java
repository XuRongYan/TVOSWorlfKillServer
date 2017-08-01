package com.rongyan.tvosworlfkillserver.mina.customfactory;

import com.rongyan.model.entity.JesusEventEntity;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Created by XRY on 2017/8/1.
 */

public class JesusEventECoder implements ProtocolEncoder {
    private final Charset charset;

    public JesusEventECoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {
        CharsetEncoder charsetEncoder = charset.newEncoder();
        JesusEventEntity o1 = (JesusEventEntity) o;
    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
