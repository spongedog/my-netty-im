package com.zkn.client.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 客户端授权消息
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAuthMessage {

    private String account;
}
