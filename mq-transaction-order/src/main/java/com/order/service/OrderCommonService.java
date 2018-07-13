package com.order.service;

import com.common.domain.OrderItemRecordDO;
import com.common.entity.ItemEntity;

import java.util.List;

public interface OrderCommonService {

    ItemEntity getItemEntityById(Long itemId);
    Long createOrderInfo(Long userId, ItemEntity item);
    List<OrderItemRecordDO> generateMessage(Long itemId, Long orderId);

}
