package com.order.service;

import com.common.common.ResponseMessage;

public interface OrderService {

    ResponseMessage<String> createOrder(Long userId, Long itemId);

}
