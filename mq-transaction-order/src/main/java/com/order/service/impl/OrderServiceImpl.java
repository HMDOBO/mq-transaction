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
         * 1. 创建订单
         *
         * 2. 调用购买记录服务，保存购买人信息
         *
         * 关键在于调用购买记录服务，spring本地事务无法使用
         *
         * 要实现程序的准确性，可用性，就需要解决这个问题
         *
         * 这里使用分布式事务解决方案之一 ———— 消息最终一致性解决方案
         *
         * 消息最终一致性：牺牲ACID中强一致性，保证系统最终一致，强调系统可用性。
         *
         * 下面，解决购买记录服务，然后集成dubbo，然后解决message消息服务、集成activeMQ
         *
         */

        return ResponseMessage.success(orderId.toString());
    }

}
