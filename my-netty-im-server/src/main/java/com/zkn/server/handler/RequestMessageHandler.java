package com.zkn.server.handler;

import com.zkn.core.enums.MessageTypeEnum;
import com.zkn.core.message.MessageHandler;
import com.zkn.server.channel.NettyChannelHolder;
import com.zkn.server.message.ClientRequestMessage;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 请求消息处理器
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/17
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RequestMessageHandler implements MessageHandler<ClientRequestMessage> {

    private final NettyChannelHolder nettyChannelHolder;

    @Override
    public void hand(Channel channel, ClientRequestMessage message) {
        log.info("receive user {} message {}", message.getFromUserId(), message.getContent());
        nettyChannelHolder.sendToUser(message.getToUserId(), message);
    }

    @Override
    public String supportType() {
        return MessageTypeEnum.REQUEST.name();
    }

    @Override
    public Class<ClientRequestMessage> messageClass() {
        return ClientRequestMessage.class;
    }
}
