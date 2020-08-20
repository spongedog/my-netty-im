package com.zkn.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/19
 */
@ConfigurationProperties(prefix = "netty")
@Component
@Setter
@Getter
public class NettyConfig {

    private String serverAddress;

    private int serverPort;
}
