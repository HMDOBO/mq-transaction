package com.order.service;

import com.common.domain.BuyRecordMessage;
import com.common.domain.OrderItemRecordDO;

import java.util.List;

public interface MessageService {

    Long saveLocalMessageToDB(List<OrderItemRecordDO> list);

    void sendLocalBuyRecordMessage(List<OrderItemRecordDO> list, Long messageId);

    BuyRecordMessage convertListToBuyRecordMessage(List<OrderItemRecordDO> list, Long messageId);

}
