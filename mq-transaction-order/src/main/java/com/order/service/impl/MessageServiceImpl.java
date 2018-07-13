package com.order.service.impl;

import com.common.common.Constants;
import com.common.common.MsgCode;
import com.common.domain.OrderItemRecordDO;
import com.common.enmus.MessageStatusEnum;
import com.common.entity.MessageEntity;
import com.common.utils.IdWorker;
import com.order.exception.OrderBizException;
import com.order.mapper.MessageEntityMapper;
import com.order.mq.producer.BuyRecordMsgProducerService;
import com.order.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageEntityMapper messageEntityMapper;
    @Autowired
    private BuyRecordMsgProducerService buyRecordMsgProducerService;

    /**
     * 消息存储到本地数据库
     *
     * @param msg
     */
    @Override
    public void saveLocalMessageToDB(String msg) {

        MessageEntity message = new MessageEntity();
        message.setId(IdWorker.getId());
        message.setQueue_name(Constants.LOCAL_BUY_RECORD_QUEUE);
        message.setMessage_body(msg);
        message.setMessage_data_type(OrderItemRecordDO.class.getName());
        message.setMessage_send_times(1);
        message.setAreadly_dead(MessageStatusEnum.NODEAD.name());

        if (messageEntityMapper.insertSelective(message) <= 0)
            throw new OrderBizException(MsgCode.INSERT_RESULT_0);

    }

    /**
     * 消息发送到ActiveMQ
     *
     * @param msg
     */
    @Override
    public void sendLocalBuyRecordMessage(String msg) {
        buyRecordMsgProducerService.sendMessage(msg);
    }


}
