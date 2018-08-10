package com.tml.nettyclient.core;

import com.tml.nettyclient.entity.Message;
import io.netty.channel.ChannelHandlerContext;

public class HandlerUtil {
    private static ChannelHandlerContext ctx = null;

    public static void setCtx(ChannelHandlerContext ctx1) {
        ctx = ctx1;
    }

    public static boolean exists() {
        return  ctx != null;
    }

    public static void send(Message msg) {
        ctx.writeAndFlush(msg);
    }

}
