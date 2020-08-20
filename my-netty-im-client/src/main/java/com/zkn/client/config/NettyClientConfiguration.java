package com.zkn.client.config;

import com.zkn.core.message.MessageDispatcher;
import com.zkn.core.message.MessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/17
 */
@Configuration
public class NettyClientConfiguration {

    @Resource
    private List<MessageHandler> messageHandlers;


    @Bean
    public MessageDispatcher messageDispatcher() {
        Map<String, MessageHandler> messageHandlerMap = messageHandlers.stream()
                .collect(Collectors.toMap(MessageHandler::supportType, x -> x));
        return new MessageDispatcher(messageHandlerMap, Executors.newCachedThreadPool());
    }
}
