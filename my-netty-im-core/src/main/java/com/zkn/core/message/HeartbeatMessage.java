package com.zkn.core.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeartbeatMessage {

    private String code = "PING";
}
