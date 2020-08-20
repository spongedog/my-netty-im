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
        channelPipeline.addLast(new IdleStateHandler(0, 30, 0))
                .addLast(new ChannelStatusHandler(nettyClient))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(MessageOuter.ImMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(messageDispatcher);
    }
}
