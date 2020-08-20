package com.zkn.server.bootstrap;

import com.zkn.core.message.MessageDispatcher;
import com.zkn.server.channel.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * netty初始化引导
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/16
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class NettyServerBootstrap {

    /**
     * 接收客户端连接
     */
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();

    /**
     * 接收客户端读写
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private final MessageDispatcher messageDispatcher;

    @Value("${netty.server.port}")
    private int port;

    @PostConstruct
    public void init() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ServerChannelInitializer(messageDispatcher))
                .option(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, false);
        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            log.info("netty server start success, port {}", port);
        }
    }
}
