package com.zkn.server.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务器响应
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerResponse {

    /**
     * 状态
     */
    private String status;

    /**
     * 用户id
     */
    private String userId;
}
