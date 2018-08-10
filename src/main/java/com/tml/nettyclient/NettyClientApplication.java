package com.tml.nettyclient;

import com.tml.nettyclient.core.HandlerUtil;
import com.tml.nettyclient.entity.Message;
import com.tml.nettyclient.handler.ClientHandler;
import com.tml.nettyclient.handler.EncoderHandler;
import com.tml.nettyclient.handler.MessageDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class NettyClientApplication {

    private static EventLoopGroup group = new NioEventLoopGroup();

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext run = SpringApplication.run(NettyClientApplication.class, args);
        Bootstrap bootStrap = (Bootstrap) run.getBean("bootStrap");
        try {
            ChannelFuture sync = bootStrap.connect("127.0.0.1", 9510).sync();
            new Thread(() -> {
                boolean flag = true;
                Scanner scanner = new Scanner(System.in);
                while (flag) {
                    System.out.println("请输入： ");
                    String next = scanner.next();
                    if(next == null || "".equals(next.trim())) {
                        flag = false;
                        continue;
                    }
                    Message message = new Message();
                    message.setFromId(2);
                    message.setContent(next);
                    message.setToId(1);
                    HandlerUtil.send(message);
                }
            }).start();
            sync.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

    @Bean("bootStrap")
    public Bootstrap bootstrap() {
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast( new EncoderHandler(),new MessageDecoder(),new ClientHandler());
                    }
                });
        return  b;
    }
}
