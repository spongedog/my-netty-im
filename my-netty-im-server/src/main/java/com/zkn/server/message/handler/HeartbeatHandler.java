package com.zkn.server.message.handler;

import com.zkn.core.message.MessageHandler;
import com.zkn.core.message.MessageOuter;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 心跳消息handler
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/20
 */
@Component
@Slf4j
public class HeartbeatHandler implements MessageHandler<MessageOuter.HeartbeatMessage> {

    @Override
    public void hand(Channel channel, MessageOuter.HeartbeatMessage message) {
        log.info("receive heart beat from channel {}", channel.id());
    }

    @Override
    public MessageOuter.ImMessage.MessageType supportType() {
        return MessageOuter.ImMessage.MessageType.HEARTBEAT;
    }

    @Override
    public Class<MessageOuter.HeartbeatMessage> messageClass() {
        return MessageOuter.HeartbeatMessage.class;
    }
}
