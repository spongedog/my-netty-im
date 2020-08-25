package com.zkn.server.message.handler;

import com.google.protobuf.Any;
import com.zkn.core.message.MessageHandler;
import com.zkn.core.message.MessageOuter;
import com.zkn.server.channel.NettyChannelHolder;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 转发用户消息给指定客户端
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/17
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestMessageHandler implements MessageHandler<MessageOuter.SendToUserMessage> {

    private final NettyChannelHolder nettyChannelHolder;

    @Override
    public void hand(Channel channel, MessageOuter.SendToUserMessage message) {
        log.info("receive user {} message {} to user {}", message.getFromUserId(), message.getContent(), message.getToUserId());
        MessageOuter.ImMessage imMessage = MessageOuter.ImMessage.newBuilder()
                .setType(supportType())
                .setPayload(Any.pack(message))
                .build();
        nettyChannelHolder.sendToUser(message.getToUserId(), imMessage);
    }

    @Override
    public MessageOuter.ImMessage.MessageType supportType() {
        return MessageOuter.ImMessage.MessageType.REQUEST;
    }

    @Override
    public Class<MessageOuter.SendToUserMessage> messageClass() {
        return MessageOuter.SendToUserMessage.class;
    }
}
