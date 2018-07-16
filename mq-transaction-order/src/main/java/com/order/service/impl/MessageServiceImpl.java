package com.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.common.common.MsgCode;
import com.common.domain.BuyRecordMessage;
import com.common.domain.OrderItemRecordDO;
import com.common.enmus.MessageDeadStatusEnum;
import com.common.enmus.MessageSendStatusEnum;
import com.common.enmus.QueueNameEnum;
import com.common.entity.MessageEntity;
import com.common.utils.IdWorker;
import com.order.exception.OrderBizException;
import com.order.mapper.MessageEntityMapper;
import com.order.mq.producer.BuyRecordMsgProducerService;
import com.order.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageEntityMapper messageEntityMapper;
    @Autowired
    private BuyRecordMsgProducerService buyRecordMsgProducerService;

    /**
     * 消息存储到本地数据库
     * @param list
     * @return
     */
    @Override
    public Long saveLocalMessageToDB(List<OrderItemRecordDO> list) {

        MessageEntity message = new MessageEntity();
        Long messageId = IdWorker.getId();
        message.setId(messageId);
        message.setQueue_name(QueueNameEnum.LOCAL_BUY_RECORD_QUEUE.getValue());

        BuyRecordMessage buyRecordMessage = convertListToBuyRecordMessage(list, messageId);
        String msg = JSON.toJSONString(buyRecordMessage);

        message.setMessage_body(msg);
        message.setMessage_data_type(OrderItemRecordDO.class.getName());
        message.setMessage_send_times(1);
        message.setAreadly_dead(MessageDeadStatusEnum.NODEAD.name());
        message.setStatus(MessageSendStatusEnum.SENDING.name());

        if (messageEntityMapper.insertSelective(message) <= 0)
            throw new OrderBizException(MsgCode.INSERT_RESULT_0);

        return messageId;
    }

    /**
     * 消息发送到ActiveMQ
     * @param list
     * @param messageId
     */
    @Override
    public void sendLocalBuyRecordMessage(List<OrderItemRecordDO> list, Long messageId) {

        BuyRecordMessage buyRecordMessage = convertListToBuyRecordMessage(list, messageId);

        buyRecordMsgProducerService.sendMessage(JSON.toJSONString(buyRecordMessage));
    }

    @Override
    public BuyRecordMessage convertListToBuyRecordMessage(List<OrderItemRecordDO> list, Long messageId) {

        BuyRecordMessage buyRecordMessage = new BuyRecordMessage();
        buyRecordMessage.setMessageId(messageId);
        buyRecordMessage.setList(list);
        buyRecordMessage.setQueueName(QueueNameEnum.LOCAL_BUY_RECORD_QUEUE.getValue());

        return buyRecordMessage;
    }
}
