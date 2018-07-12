package com.order.controller;

import com.common.common.ResponseMessage;
import com.common.utils.IdWorker;
import com.order.entity.OrderEntity;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("create")
    public ResponseMessage<String> createOrder() {
        Long userId = IdWorker.getId();
        Long itemId = 1L;

        ResponseMessage<String> message = orderService.createOrder(userId, itemId);

        return message;
    }

    @RequestMapping("getOrderInfo")
    public ResponseMessage<OrderEntity> getOrderInfo(Long orderId) {
        return orderService.getOrder(orderId);
    }

}
