package com.order.service.impl;

import com.common.common.MsgCode;
import com.common.common.ResponseMessage;
import com.common.exception.OrderBizException;
import com.common.utils.IdWorker;
import com.order.entity.OrderEntity;
import com.order.enums.OrderStatusEnum;
import com.order.mapper.OrderEntityMapper;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Override
    public ResponseMessage<String> createOrder(Long userId, Long itemId) {

        OrderEntity order = new OrderEntity();
        Long orderId = IdWorker.getId();
        order.setId(orderId);
        order.setUser_id(userId);
        order.setItem_id(itemId);
        order.setItem_name("iPhone X");
        order.setItem_price(888800L);
        order.setItem_count(1);
        order.setTotal_price(888800L);
        order.setStatus(OrderStatusEnum.UNPAID.getCode());

        if (orderEntityMapper.insertSelective(order) <= 0)
            throw new OrderBizException(MsgCode.INSERT_RESULT_0);

        /*
         * 1.
         * 2.
         * 3.
         */

        return ResponseMessage.success(orderId.toString());
    }

}
