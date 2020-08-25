package com.zkn.client.message;

import lombok.Data;

/**
 * 来自用户的消息
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/18
 */
@Data
public class UserMessage {

    /**
     * 发送用户id
     */
    private String fromUserId;

    /**
     * 接收用户id
     */
    private String toUserId;

    /**
     * 消息内容
     */
    private String content;
}
