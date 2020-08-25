package com.zkn.client.message.handler;

import com.zkn.client.cache.UserCache;
import com.zkn.core.message.MessageHandler;
import com.zkn.core.message.MessageOuter;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ServerResponseHandler implements MessageHandler<MessageOuter.ServerResponse> {

    private final UserCache userCache;

    @Override
    public void hand(Channel channel, MessageOuter.ServerResponse message) {
        log.info("receive server response {}", message.getStatus());
        Optional.ofNullable(message.getUserId()).ifPresent(userCache::setUserId);
    }

    @Override
    public MessageOuter.ImMessage.MessageType supportType() {
        return MessageOuter.ImMessage.MessageType.RESPONSE;
    }

    @Override
    public Class<MessageOuter.ServerResponse> messageClass() {
        return MessageOuter.ServerResponse.class;
    }
}
