package com.zkn.client.channel;

import com.google.protobuf.Any;
import com.zkn.client.bootstrap.NettyClient;
import com.zkn.core.message.MessageOuter;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/20
 */
@RequiredArgsConstructor
@Slf4j
public class ChannelStatusHandler extends ChannelInboundHandlerAdapter {

    private final NettyClient nettyClient;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        nettyClient.connect();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("channel id {}, exception caught {}", ctx.channel().id(), cause);
        ctx.channel().close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        //发起心跳
        if (event instanceof IdleStateEvent) {
            log.info("send heart beat");
            MessageOuter.HeartbeatMessage heartbeatMessage = MessageOuter.HeartbeatMessage.newBuilder()
                    .setCode("PING")
                    .build();
            MessageOuter.ImMessage imMessage = MessageOuter.ImMessage.newBuilder()
                    .setType(MessageOuter.ImMessage.MessageType.HEARTBEAT)
                    .setPayload(Any.pack(heartbeatMessage))
                    .build();
            ctx.channel().writeAndFlush(imMessage).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
        } else {
            super.userEventTriggered(ctx, event);
        }
    }
}
