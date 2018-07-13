package com.order.service;

import com.common.common.ResponseMessage;
import com.order.entity.OrderEntity;

public interface OrderService {

    ResponseMessage<OrderEntity> getOrder(Long orderId);

    ResponseMessage<String> createOrder(Long userId, Long itemId);

//    ResponseMessage<String> createOrderWithLocalMQ(Long userId, Long itemId);

}
