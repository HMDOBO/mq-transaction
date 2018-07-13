package com.item.api.service;

import com.common.domain.BuyRecordMessage;
import com.common.domain.OrderItemRecordDO;

import java.util.List;

public interface ItemService {

    /**
     * 记录购买信息OrderItemRecordDO
     */
    void recordOrderItem(List<OrderItemRecordDO> list);

    void buyRecordAndConfirmMessageSuccess(BuyRecordMessage message);

}
