package com.order.mq.producer.impl;

import com.order.mq.producer.BuyRecordMsgProducerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Queue;

@SuppressWarnings("ALL")
@Component
public class BuyRecordMsgProducerServiceImpl implements BuyRecordMsgProducerService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BuyRecordMsgProducerServiceImpl.class);

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Resource(name = "localBuyRecordQueue")
    private Queue localBuyRecordQueue;

    @Override
    public void sendMessage(String message) {
        this.jmsMessagingTemplate.convertAndSend(this.localBuyRecordQueue, message);
        LOG.info("=========消息发送成功===========：" + message);
    }
}
