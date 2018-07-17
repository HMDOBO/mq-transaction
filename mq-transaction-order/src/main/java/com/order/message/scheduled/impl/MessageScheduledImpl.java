package com.order.message.scheduled.impl;

import com.common.entity.MessageEntity;
import com.order.message.filter.MessageFilter;
import com.order.message.scheduled.MessageScheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageScheduledImpl implements MessageScheduled {

    @Autowired
    private MessageFilter messageFilter;
    @Override
    public void handleSendingTimeOutMessage() {

        // 1. 查询需要发送的消息集合
        int pageSize = 500; // 每页查询多少条
        int maxHandlerPageCount = 3;    // 查询最大页数
        Map<String, MessageEntity> messageMap = messageFilter.selectSendingTimeOutMessage(pageSize, maxHandlerPageCount);

        // 2. 单个发送消息
        messageFilter.sendEligibleMessage(messageMap);
    }

}
