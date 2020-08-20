package com.zkn.client.message;

import com.zkn.core.message.RequestMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 来自用户的消息
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserMessage extends RequestMessage {

    /**
     * 发送用户id
     */
    private String fromUserId;

    /**
     * 接收用户id
     */
    private String toUserId;
}
