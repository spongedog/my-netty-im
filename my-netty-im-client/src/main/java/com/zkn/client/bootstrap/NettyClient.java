package com.zkn.client.bootstrap;

import ch.qos.logback.core.util.TimeUtil;
import com.zkn.client.channel.ClientChannelInitializer;
import com.zkn.client.config.NettyConfig;
import com.zkn.core.message.MessageDispatcher;
import com.zkn.core.message.MessageOuter;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/18
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NettyClient {

    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private final NettyConfig nettyConfig;

    private final MessageDispatcher messageDispatcher;

    private Channel channel;

    @PostConstruct
    public void connect() {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(nettyConfig.getServerAddress(), nettyConfig.getServerPort())
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .handler(new ClientChannelInitializer(messageDispatcher, this));

        bootstrap.connect().addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                channel = future.channel();
                log.info("netty client connect server {} : {} success", nettyConfig.getServerAddress(), nettyConfig.getServerPort());
            } else {
                log.error("netty client connect server {} : {} failure, try again after 30 s", nettyConfig.getServerAddress(), nettyConfig.getServerPort());
                workerGroup.schedule(this::connect, 30 , TimeUnit.SECONDS);
            }
        });
    }

    @PreDestroy
    public void close() {
        if (channel != null) {
            channel.close();
        }
    }

    public void send(MessageOuter.ImMessage message) {
        if (channel != null) {
            channel.writeAndFlush(message);
        }
    }

}
