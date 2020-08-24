package com.zkn.core.message;

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

    private final Map<MessageOuter.ImMessage.MessageType, MessageHandler> messageHandlerMap;

    private final ExecutorService executorService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageOuter.ImMessage msg) throws Exception {
        Optional.ofNullable(messageHandlerMap.get(msg.getType())).ifPresentOrElse(messageHandler -> {
            Object payload = JsonUtil.readValue(msg.getPayload(), messageHandler.messageClass());

            CompletableFuture.runAsync(() -> messageHandler.hand(ctx.channel(), payload), executorService)
                    .exceptionallyAsync(e -> {
                        ctx.fireExceptionCaught(e);
                        return null;
                    });
        }, () -> log.warn("there is no handler for messageType {}", msg.getType()));


    }

}
