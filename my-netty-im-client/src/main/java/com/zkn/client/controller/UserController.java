package com.zkn.client.controller;

import com.zkn.client.bootstrap.NettyClient;
import com.zkn.client.cache.UserCache;
import com.zkn.client.message.ClientAuthMessage;
import com.zkn.client.message.UserMessage;
import com.zkn.core.enums.MessageTypeEnum;
import com.zkn.core.message.MessageOuter;
import com.zkn.core.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        MessageOuter.ImMessage imMessage = MessageOuter.ImMessage.newBuilder()
                .setPayload(JsonUtil.transToString(new ClientAuthMessage(account)))
                .setType(MessageTypeEnum.AUTH.name())
                .build();
        nettyClient.send(imMessage);
    }

    @PostMapping("/send")
    public void send(@RequestBody UserMessage request) {
        userCache.getUserId().ifPresent(userId -> {
            request.setFromUserId(userId);
            request.setId(UUID.randomUUID().toString().replace("-", ""));
            MessageOuter.ImMessage imMessage = MessageOuter.ImMessage.newBuilder()
                    .setPayload(JsonUtil.transToString(request))
                    .setType(MessageTypeEnum.REQUEST.name())
                    .build();
            nettyClient.send(imMessage);
        });

    }
}
