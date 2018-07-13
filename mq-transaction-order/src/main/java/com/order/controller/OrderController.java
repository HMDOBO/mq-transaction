package com.order.controller;

import com.common.common.ResponseMessage;
import com.common.utils.IdWorker;
import com.order.entity.OrderEntity;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
//    @Qualifier("normalOrderService")
    @Qualifier("localMessageOrderService")
    private OrderService orderService;

    @RequestMapping("create")
    public ResponseMessage<String> createOrder() {
        Long userId = IdWorker.getId();
        Long itemId = 1017703866482876418L;

        ResponseMessage<String> result = orderService.createOrder(userId, itemId);

        return result;
    }

    @RequestMapping("getOrderInfo")
    public ResponseMessage<OrderEntity> getOrderInfo(Long orderId) {
        return orderService.getOrder(orderId);
    }

}
