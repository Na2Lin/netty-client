package com.tml.nettyclient.handler;

import com.alibaba.fastjson.JSONObject;
import com.tml.nettyclient.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MessageDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        String s = new String(ByteBufUtil.getBytes(byteBuf), CharsetUtil.UTF_8);
        Message message = JSONObject.parseObject(s, Message.class);
        list.add(message);
        byteBuf.skipBytes(byteBuf.readableBytes());
    }
}
