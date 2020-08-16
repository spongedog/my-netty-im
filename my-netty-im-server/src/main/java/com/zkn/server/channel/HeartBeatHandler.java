package com.zkn.server.channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 心跳handler
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/16
 */
@Component
@RequiredArgsConstructor
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private final NettyChannelHolder channelHolder;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (IdleState.READER_IDLE == state) {
                channelHolder.removeChannel(ctx.channel().id());
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }
}
