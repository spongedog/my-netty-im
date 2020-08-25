package com.zkn.core.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.zkn.core.exception.BaseException;
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

    private final Map<MessageOuter.ImMessage.MessageType, MessageHandler<? extends Message>> messageHandlerMap;

    private final ExecutorService executorService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageOuter.ImMessage msg) throws Exception {
        Optional.ofNullable(messageHandlerMap.get(msg.getType()))
                .ifPresentOrElse(messageHandler -> doHand(ctx, messageHandler, msg),
                        () -> log.warn("there is no handler for messageType {}", msg.getType()));
    }

    private <T extends Message> void doHand(ChannelHandlerContext ctx, MessageHandler<T> messageHandler, MessageOuter.ImMessage msg) {
        try {
            T payload = msg.getPayload().unpack(messageHandler.messageClass());
            CompletableFuture.runAsync(() -> messageHandler.hand(ctx.channel(), payload), executorService)
                    .exceptionallyAsync(e -> {
                        ctx.fireExceptionCaught(e);
                        return null;
                    });
        } catch (InvalidProtocolBufferException e) {
            throw new BaseException(e);
        }
    }

}
