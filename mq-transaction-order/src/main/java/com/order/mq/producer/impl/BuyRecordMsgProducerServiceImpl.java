package com.order.mq.producer.impl;

import com.alibaba.fastjson.JSON;
import com.common.domain.OrderItemRecordDO;
import com.order.mq.producer.BuyRecordMsgProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;

@SuppressWarnings("ALL")
@Component
public class BuyRecordMsgProducerServiceImpl implements BuyRecordMsgProducerService {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Resource(name = "localBuyRecordQueue")
    private Queue localBuyRecordQueue;

    @Override
    public void sendMessage(String message) {
        System.out.println("=========消息发送成功===========：" + message);
        this.jmsMessagingTemplate.convertAndSend(this.localBuyRecordQueue, message);
    }
}
