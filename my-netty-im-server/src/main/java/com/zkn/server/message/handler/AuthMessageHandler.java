package com.zkn.server.message.handler;

import com.zkn.core.message.MessageHandler;
import com.zkn.core.message.MessageOuter;
import com.zkn.core.util.JsonUtil;
import com.zkn.server.channel.NettyChannelHolder;
import com.zkn.server.message.ClientAuthMessage;
import com.zkn.server.message.ServerResponse;
import com.zkn.server.repository.UserDao;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 授权消息处理器
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthMessageHandler implements MessageHandler<ClientAuthMessage> {

    private final NettyChannelHolder nettyChannelHolder;

    private final UserDao userDao;

    @Override
    public void hand(Channel channel, ClientAuthMessage message) {
        userDao.findByAccount(message.getAccount())
                .ifPresentOrElse(user -> {
                    nettyChannelHolder.saveChannel(user.getId(), channel);
                    this.sendResponse("SUCCESS", user.getId(), channel);
                }, () -> this.sendResponse("FAILURE", null, channel));
    }

    @Override
    public MessageOuter.ImMessage.MessageType supportType() {
        return MessageOuter.ImMessage.MessageType.AUTH;
    }

    @Override
    public Class<ClientAuthMessage> messageClass() {
        return ClientAuthMessage.class;
    }

    private void sendResponse(String status, String userId, Channel channel) {
        MessageOuter.ImMessage imMessage = MessageOuter.ImMessage.newBuilder()
                .setType(MessageOuter.ImMessage.MessageType.RESPONSE)
                .setPayload(JsonUtil.transToString(new ServerResponse(status, userId)))
                .build();
        channel.writeAndFlush(imMessage);
    }
}
