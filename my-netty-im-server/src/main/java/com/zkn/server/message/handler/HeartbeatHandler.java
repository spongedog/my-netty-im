package com.zkn.server.message.handler;

import com.zkn.core.enums.MessageTypeEnum;
import com.zkn.core.message.HeartbeatMessage;
import com.zkn.core.message.MessageHandler;
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
public class HeartbeatHandler implements MessageHandler<HeartbeatMessage> {

    @Override
    public void hand(Channel channel, HeartbeatMessage message) {
        log.info("receive heart beat from channel {}", channel.id());
    }

    @Override
    public String supportType() {
        return MessageTypeEnum.HEARTBEAT.name();
    }

    @Override
    public Class<HeartbeatMessage> messageClass() {
        return HeartbeatMessage.class;
    }
}
