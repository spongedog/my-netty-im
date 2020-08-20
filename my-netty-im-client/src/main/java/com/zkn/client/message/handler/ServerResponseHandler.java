package com.zkn.client.message.handler;

import com.zkn.client.cache.UserCache;
import com.zkn.client.message.ServerResponse;
import com.zkn.core.enums.MessageTypeEnum;
import com.zkn.core.message.MessageHandler;
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
public class ServerResponseHandler implements MessageHandler<ServerResponse> {

    private final UserCache userCache;

    @Override
    public void hand(Channel channel, ServerResponse message) {
        log.info("receive server response {}", message.getStatus());
        Optional.ofNullable(message.getUserId()).ifPresent(userCache::setUserId);
    }

    @Override
    public String supportType() {
        return MessageTypeEnum.RESPONSE.name();
    }

    @Override
    public Class<ServerResponse> messageClass() {
        return ServerResponse.class;
    }
}
