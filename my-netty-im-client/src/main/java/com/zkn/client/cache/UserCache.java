package com.zkn.client.cache;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/20
 */
@Component
public class UserCache {

    private String userId;

    public Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }

    public synchronized void setUserId(String userId) {
        this.userId = userId;
    }
}
