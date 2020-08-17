package com.zkn.core.message;

import lombok.Data;

/**
 * 请求消息
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/17
 */
@Data
public class RequestMessage {

    /**
     * 消息id
     */
    private String id;

    /**
     * 消息内容
     */
    private String content;
}
