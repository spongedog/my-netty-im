package com.zkn.client.message.handler;

import com.zkn.core.message.MessageHandler;
import com.zkn.core.message.MessageOuter;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 用户消息handler
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/18
 */
@Component
@Slf4j
public class UserMessageHandler implements MessageHandler<MessageOuter.SendToUserMessage> {

    @Override
    public void hand(Channel channel, MessageOuter.SendToUserMessage message) {
        log.info("receive user {} message {}", message.getFromUserId(), message.getContent());
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
