package com.zkn.client.channel;

import com.zkn.client.bootstrap.NettyClient;
import com.zkn.core.message.MessageDispatcher;
import com.zkn.core.message.MessageOuter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;

/**
 * 客户端通道初始化
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/18
 */
@RequiredArgsConstructor
public class ClientChannelInitializer extends ChannelInitializer<Channel> {

    private final MessageDispatcher messageDispatcher;

    private final NettyClient nettyClient;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();

        /*
         * 1. 监听写超时事件，超时时间设为15s
         * 2. 处理写超时发起心跳，断线重连等
         * 2. 解析proto协议消息头中的长度字段
         * 3. proto解码器
         * 4. 对proto协议消息头增加一个整形字段用于标识消息长度，用于解决TCP粘包半包问题
         * 5. proto编码器
         * 6. 自定义消息分发器
         */
        channelPipeline.addLast(new IdleStateHandler(0, 20, 0))
                .addLast(new ChannelStatusHandler(nettyClient))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(MessageOuter.ImMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(messageDispatcher);
    }
}
