package com.zkn.server.channel;

import com.zkn.core.message.ImMessageOuterClass;
import com.zkn.core.message.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/14
 */
@RequiredArgsConstructor
public class ServerChannelInitializer extends ChannelInitializer<Channel> {

    private final MessageDispatcher messageDispatcher;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        channelPipeline.addLast(new IdleStateHandler(0, 0, 30))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(ImMessageOuterClass.ImMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(messageDispatcher);
    }
}
