package com.tml.nettyclient.handler;

import com.tml.nettyclient.core.HandlerUtil;
import com.tml.nettyclient.entity.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

@ChannelHandler.Sharable
public class ClientHandler extends ChannelInboundHandlerAdapter {


            @Override
    public void channelActive(ChannelHandlerContext ctx) {
                Message message = new Message();
                message.setFromId(2);
//                ByteBuf byteBuf = Unpooled.copiedBuffer(message.toString(), CharsetUtil.UTF_8);
                ctx.writeAndFlush(message);
                if(!HandlerUtil.exists()) {
                    HandlerUtil.setCtx(ctx);
                }
            }

            @Override
     public void channelRead(ChannelHandlerContext ctx, Object msg) {
                System.out.println(((Message)msg).getContent());
//                ctx.write(msg);
            }

           @Override
      public void channelReadComplete(ChannelHandlerContext ctx) {
//                ctx.flush();
              }

              @Override
      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                  // Close the connection when an exception is raised.
                 cause.printStackTrace();
              ctx.close();
           }
}
