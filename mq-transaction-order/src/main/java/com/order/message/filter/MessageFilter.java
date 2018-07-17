package com.order.message.filter;

import com.common.entity.MessageEntity;

import java.util.Map;

/**
 * 消息查询过滤器
 *
 * 查询符合重发条件的消息
 */
public interface MessageFilter {

    /**
     * 查询发送中，但已经超时的消息
     * @param pageSize  每页查询数量
     * @param maxHandlerPageCount   最多查询页数
     * @return
     */
    Map<String, MessageEntity> selectSendingTimeOutMessage(int pageSize, int maxHandlerPageCount);

    /**
     * 重新发送符合超时规则的消息
     * @param messageMap
     */
    void sendEligibleMessage(Map<String, MessageEntity> messageMap);

}
