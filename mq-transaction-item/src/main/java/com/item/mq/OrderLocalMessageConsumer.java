package com.item.mq;

import com.alibaba.fastjson.JSON;
import com.common.common.Constants;
import com.common.domain.BuyRecordMessage;
import com.item.api.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;


/**
 * mq消息消费者
 */
@Service
public class OrderLocalMessageConsumer {
	
	private static final Logger LOG = LoggerFactory.getLogger(OrderLocalMessageConsumer.class);

    @Autowired
    private ItemService itemService;

    @JmsListener(destination = Constants.LOCAL_BUY_RECORD_QUEUE)
    public void receiveMessage(String msg) {

    	LOG.info("商品购买记录服务接收到消息：" + msg);

        BuyRecordMessage message = JSON.parseObject(msg, BuyRecordMessage.class);

        // 商品购买记录，消息状态更改
        itemService.buyRecordAndConfirmMessageSuccess(message);
    }

}
