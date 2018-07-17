package com.order.service;

import com.common.domain.BuyRecordMessage;
import com.common.domain.OrderItemRecordDO;
import com.common.entity.MessageEntity;

import java.util.List;

public interface MessageService {

    Long saveLocalMessageToDB(List<OrderItemRecordDO> list);

    void sendLocalBuyRecordMessage(List<OrderItemRecordDO> list, Long messageId);

    BuyRecordMessage convertListToBuyRecordMessage(List<OrderItemRecordDO> list, Long messageId);

    /**
     * 标记消息为死亡状态
     * @param messageId
     */
    void markedAsDeath(Long messageId);

    void resendMessage(MessageEntity message);

}
