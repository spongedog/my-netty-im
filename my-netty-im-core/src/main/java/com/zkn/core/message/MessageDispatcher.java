package com.zkn.core.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 消息分发器
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/14
 */
@RequiredArgsConstructor
public class MessageDispatcher extends SimpleChannelInboundHandler<ImMessageOuterClass.ImMessage> {

    private final Map<String, MessageHandler> messageHandlerMap;

    private final ExecutorService executorService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @SuppressWarnings("unchecked")
    protected void channelRead0(ChannelHandlerContext ctx, ImMessageOuterClass.ImMessage msg) throws Exception {
        MessageHandler<Object> messageHandler = messageHandlerMap.get(msg.getType());

        Object payload = objectMapper.readValue(msg.getPayload(), messageHandler.messageClass());
        executorService.submit(() -> messageHandler.hand(ctx.channel(), payload));
    }

}
