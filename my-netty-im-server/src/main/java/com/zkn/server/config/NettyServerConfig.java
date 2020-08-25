package com.zkn.server.config;

import com.google.protobuf.Message;
import com.zkn.core.message.MessageDispatcher;
import com.zkn.core.message.MessageHandler;
import com.zkn.core.message.MessageOuter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/17
 */
@Configuration
public class NettyServerConfig {

    @Resource
    private List<MessageHandler<? extends Message>> messageHandlers;


    @Bean
    public MessageDispatcher messageDispatcher() {
        Map<MessageOuter.ImMessage.MessageType, MessageHandler<? extends Message>> messageHandlerMap = messageHandlers.stream()
                .collect(Collectors.toMap(MessageHandler::supportType, x -> x));
        return new MessageDispatcher(messageHandlerMap, Executors.newCachedThreadPool());
    }
}
