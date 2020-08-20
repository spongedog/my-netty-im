package com.zkn.server.channel;

import com.zkn.core.message.MessageOuter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理用户的 {@link Channel}
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/16
 */
@Component
@Slf4j
public class NettyChannelHolder {

    private final Map<String, Channel> userChannelMap = new ConcurrentHashMap<>();

    private final Map<ChannelId, Channel> channelIdMap = new ConcurrentHashMap<>();

    private static final AttributeKey<String> USER_ID = AttributeKey.newInstance("user_id");

    public void saveChannel(String userId, Channel channel) {
        channel.attr(USER_ID).set(userId);
        userChannelMap.put(userId, channel);
    }

    public void saveChannel(Channel channel) {
        channelIdMap.put(channel.id(), channel);
    }

    public void removeChannel(ChannelId channelId) {
        log.info("remove channel {}", channelId);
        Optional.ofNullable(channelIdMap.remove(channelId))
                .filter(channel -> channel.hasAttr(USER_ID))
                .ifPresent(channel ->  userChannelMap.remove(channel.attr(USER_ID).get()));
    }

    public void sendToUser(String userId, MessageOuter.ImMessage message) {
        Optional.ofNullable(userChannelMap.get(userId))
                .filter(Channel::isActive)
                .ifPresentOrElse(channel -> channel.writeAndFlush(message),
                        () -> log.info("com.zkn.client.channel not active"));
    }
}
