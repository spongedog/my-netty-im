package com.zkn.core.message;

import com.zkn.core.exception.BaseException;
import com.zkn.core.util.JsonUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * 消息分发器
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/14
 */
@RequiredArgsConstructor
@ChannelHandler.Sharable
@Slf4j
public class MessageDispatcher extends SimpleChannelInboundHandler<MessageOuter.ImMessage> {

    private final Map<String, MessageHandler> messageHandlerMap;

    private final ExecutorService executorService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageOuter.ImMessage msg) throws Exception {
        MessageHandler<Object> messageHandler = messageHandlerMap.get(msg.getType());

        if (messageHandler == null) {
            log.error("not legal type {}", msg.getType());
        }


        Object payload = JsonUtil.readValue(msg.getPayload(), messageHandler.messageClass());

        CompletableFuture.runAsync(() -> messageHandler.hand(ctx.channel(), payload), executorService)
                .exceptionallyAsync(e -> {
                    ctx.fireExceptionCaught(e);
                    return null;
                });
    }

}
