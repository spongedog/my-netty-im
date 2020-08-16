package com.zkn.server.channel;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 郑凯努
 * @version 1.0
 * @date 2020/8/16
 */
@Component
public class NettyChannelHolder {

    private Map<String, Channel> userChannelMap = new ConcurrentHashMap<String, Channel>();

    private Map<ChannelId, Channel> channelIdMap = new ConcurrentHashMap<>();

    private static final AttributeKey<String> USER_ID = AttributeKey.newInstance("user_id");

    public void saveChannel(String userId, Channel channel) {
        channel.attr(USER_ID).set(userId);
        channelIdMap.put(channel.id(), channel);
        userChannelMap.put(userId, channel);
    }

    public void removeChannel(ChannelId channelId) {
        Channel channel = channelIdMap.remove(channelId);
        if (channel != null) {
            userChannelMap.remove(channel.attr(USER_ID));
        }
    }
}
