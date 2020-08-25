package com.zkn.client.controller;

import com.google.protobuf.Any;
import com.zkn.client.bootstrap.NettyClient;
import com.zkn.client.cache.UserCache;
import com.zkn.client.message.UserMessage;
import com.zkn.core.message.MessageOuter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final NettyClient nettyClient;

    private final UserCache userCache;

    @PostMapping("/authentication/{account}")
    public void authentication(@PathVariable String account) {
        MessageOuter.ClientAuthMessage clientAuthMessage = MessageOuter.ClientAuthMessage
                .newBuilder()
                .setAccount(account)
                .build();

        MessageOuter.ImMessage imMessage = MessageOuter.ImMessage.newBuilder()
                .setPayload(Any.pack(clientAuthMessage))
                .setType(MessageOuter.ImMessage.MessageType.AUTH)
                .build();
        nettyClient.send(imMessage);
    }

    @PostMapping("/send")
    public void send(@RequestBody UserMessage request) {
        userCache.getUserId().ifPresent(userId -> {
            MessageOuter.SendToUserMessage sendToUserMessage = MessageOuter.SendToUserMessage
                    .newBuilder()
                    .setFromUserId(userId)
                    .setToUserId(request.getToUserId())
                    .setContent(request.getContent())
                    .build();

            MessageOuter.ImMessage imMessage = MessageOuter.ImMessage.newBuilder()
                    .setPayload(Any.pack(sendToUserMessage))
                    .setType(MessageOuter.ImMessage.MessageType.REQUEST)
                    .build();
            nettyClient.send(imMessage);
        });

    }
}
