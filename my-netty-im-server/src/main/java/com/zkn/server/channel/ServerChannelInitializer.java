package com.zkn.server.channel;

import com.zkn.core.message.MessageDispatcher;
import com.zkn.core.message.MessageOuter;
import com.zkn.server.handler.ChannelStatusHandler;
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
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/14
 */
@RequiredArgsConstructor
public class ServerChannelInitializer extends ChannelInitializer<Channel> {

    private final MessageDispatcher messageDispatcher;

    private final ChannelStatusHandler channelStatusHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline channelPipeline = ch.pipeline();
        /*
         * 1.监听读超时事件，超时时间设为30s
         * 2. 解析proto协议消息头中的长度字段
         * 3. proto解码器
         * 4. 对proto协议消息头增加一个整形字段用于标识消息长度，用于解决TCP粘包半包问题
         * 5. proto编码器
         * 6. 自定义消息分发器
         * 7. 对channel的上下线，超时事件的处理
         */
        channelPipeline.addLast(new IdleStateHandler(30, 0, 0))
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(MessageOuter.ImMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(messageDispatcher)
                .addLast(channelStatusHandler);
    }
}
