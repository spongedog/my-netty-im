package com.zkn.client.message.handler;

import com.zkn.client.message.UserMessage;
import com.zkn.core.enums.MessageTypeEnum;
import com.zkn.core.message.MessageHandler;
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
public class UserMessageHandler implements MessageHandler<UserMessage> {

    @Override
    public void hand(Channel channel, UserMessage message) {
        log.info("receive user {} message {}", message.getFromUserId(), message.getContent());
    }

    @Override
    public String supportType() {
        return MessageTypeEnum.REQUEST.name();
    }

    @Override
    public Class<UserMessage> messageClass() {
        return UserMessage.class;
    }
}
