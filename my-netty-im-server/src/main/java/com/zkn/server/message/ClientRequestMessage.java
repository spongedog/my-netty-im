package com.zkn.server.message;

import com.zkn.core.message.RequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 客户端请求消息
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/17
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ClientRequestMessage extends RequestMessage {

    /**
     * 发送用户id
     */
    private String fromUserId;

    /**
     * 接收用户id
     */
    private String toUserId;
}
