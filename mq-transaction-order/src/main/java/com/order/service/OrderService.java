package com.order.service;

import com.common.common.ResponseMessage;
import com.order.entity.OrderEntity;

public interface OrderService {

    ResponseMessage<String> createOrder(Long userId, Long itemId);

    ResponseMessage<OrderEntity> getOrder(Long orderId);

}
