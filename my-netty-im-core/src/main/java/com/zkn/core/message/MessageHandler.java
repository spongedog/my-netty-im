package com.zkn.core.message;

import io.netty.channel.Channel;

/**
 * 消息处理器接口
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/14
 */
public interface MessageHandler<T> {

    /**
     * 处理消息
     * @param channel netty com.zkn.client.channel
     * @param message 消息实体
     */
    void hand(Channel channel, T message);

    /**
     * @return 返回处理器支持的消息类型
     */
    MessageOuter.ImMessage.MessageType supportType();

    /**
     * @return 返回处理器支持的消息类型
     */
    Class<T> messageClass();
}
