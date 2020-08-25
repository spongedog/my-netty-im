package com.zkn.server.handler;

import com.zkn.server.channel.NettyChannelHolder;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 通道状态handler
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/16
 */
@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class ChannelStatusHandler extends ChannelInboundHandlerAdapter {

    private final NettyChannelHolder channelHolder;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelHolder.saveChannel(ctx.channel());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        channelHolder.removeChannel(ctx.channel().id());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            //读超时，移除对应的channel
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
        channelHolder.removeChannel(ctx.channel().id());
        ctx.close();
    }

}
