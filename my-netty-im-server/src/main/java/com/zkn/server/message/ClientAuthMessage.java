package com.zkn.server.message;

import lombok.Data;

/**
 * 客户端授权消息
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
@Data
public class ClientAuthMessage {

    private String account;
}
