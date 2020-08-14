package com.zkn.core.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/14
 */
@RequiredArgsConstructor
public class MessageDispatcher extends SimpleChannelInboundHandler<ImMessageOuterClass.ImMessage> {

    private final Map<String, MessageHandler<?>> messageHandlerMap;

    private final ExecutorService executorService;

    private final ObjectMapper objectMapper;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ImMessageOuterClass.ImMessage msg) throws Exception {
        MessageHandler messageHandler = messageHandlerMap.get(msg.getType());

        Object payload = objectMapper.readValue(msg.getPayload(), messageHandler.messageClass());
        executorService.submit(() -> messageHandler.hand(ctx.channel(), payload));
    }
}
