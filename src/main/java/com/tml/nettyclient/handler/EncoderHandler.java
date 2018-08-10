package com.tml.nettyclient.handler;

import com.alibaba.fastjson.JSONObject;
import com.tml.nettyclient.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class EncoderHandler extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        Message msg = (Message) o;
        String s = JSONObject.toJSONString(msg);
        byteBuf.writeBytes(s.getBytes());
    }
}
