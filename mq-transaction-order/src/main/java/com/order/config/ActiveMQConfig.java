package com.order.config;

import com.common.common.Constants;
import com.common.enmus.QueueNameEnum;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@Configuration
@EnableJms
public class ActiveMQConfig {

    /**
     * 商品购买记录队列
     * @return
     */
    @Bean("localBuyRecordQueue")
    public Queue localQueue() {
        return new ActiveMQQueue(QueueNameEnum.LOCAL_BUY_RECORD_QUEUE.getValue());
    }

}
