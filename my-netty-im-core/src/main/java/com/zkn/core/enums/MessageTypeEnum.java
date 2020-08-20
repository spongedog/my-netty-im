package com.zkn.core.enums;

/**
 * 消息类型枚举
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/17
 */
public enum MessageTypeEnum {

    /**
     * 请求消息
     */
    REQUEST,

    /**
     * 响应消息
     */
    RESPONSE,

    /**
     * 心跳
     */
    HEARTBEAT,
    /**
     * 授权消息
     */
    AUTH;
}
