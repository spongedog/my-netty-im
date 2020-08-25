package com.zkn.server.message.handler;

import com.google.protobuf.Any;
import com.zkn.core.message.MessageHandler;
import com.zkn.core.message.MessageOuter;
import com.zkn.server.channel.NettyChannelHolder;
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
public class AuthMessageHandler implements MessageHandler<MessageOuter.ClientAuthMessage> {

    private final NettyChannelHolder nettyChannelHolder;

    private final UserDao userDao;

    @Override
    public void hand(Channel channel, MessageOuter.ClientAuthMessage message) {
        userDao.findByAccount(message.getAccount())
                .ifPresentOrElse(user -> {
                    nettyChannelHolder.saveChannel(user.getId(), channel);
                    this.sendResponse("SUCCESS", user.getId(), channel);
                }, () -> this.sendResponse("FAILURE", "", channel));
    }

    @Override
    public MessageOuter.ImMessage.MessageType supportType() {
        return MessageOuter.ImMessage.MessageType.AUTH;
    }

    @Override
    public Class<MessageOuter.ClientAuthMessage> messageClass() {
        return MessageOuter.ClientAuthMessage.class;
    }

    private void sendResponse(String status, String userId, Channel channel) {
        MessageOuter.ServerResponse serverResponse = MessageOuter.ServerResponse.newBuilder()
                .setUserId(userId)
                .setStatus(status)
                .build();

        MessageOuter.ImMessage imMessage = MessageOuter.ImMessage.newBuilder()
                .setType(MessageOuter.ImMessage.MessageType.RESPONSE)
                .setPayload(Any.pack(serverResponse))
                .build();
        channel.writeAndFlush(imMessage);
    }
}
