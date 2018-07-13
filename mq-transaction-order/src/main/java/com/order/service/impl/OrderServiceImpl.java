package com.order.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.common.common.MsgCode;
import com.common.common.ResponseMessage;
import com.common.domain.OrderItemRecordDO;
import com.common.utils.IdWorker;
import com.item.api.service.ItemService;
import com.order.entity.OrderEntity;
import com.order.enums.OrderStatusEnum;
import com.order.exception.OrderBizException;
import com.order.mapper.OrderEntityMapper;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单服务实现
 *
 * 简单服务调用，出现分布式事务场景模拟
 */
//@Service("normalOrderService")    // 测试mq可靠消息，将dubbo有关调用注掉
public class OrderServiceImpl implements OrderService {

    @Reference(version = "1.0.0",
        application = "${dubbo.application.id}",
        url = "dubbo://localhost:12345")
    private ItemService itemService;
    @Autowired
    private OrderEntityMapper orderEntityMapper;


    /**
     * 用户下单操作
     *
     * 1. 生成订单
     * 2. 记录购买人信息
     *
     * 对于dubbo服务调用，显然spring本地事务不再有效
     *
     * 因此要寻找一种方法替代spring本地事务，达到一样的效果
     *
     * 解决办法：可靠消息最终一致性
     *
     * @param userId
     * @param itemId
     * @return
     */
    @Transactional
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

        // 调用购买记录服务
        List<OrderItemRecordDO> list = new ArrayList<>();
        OrderItemRecordDO oird = new OrderItemRecordDO();
        oird.setItem_id(itemId);
        oird.setOrder_id(orderId);
        oird.setUsername(userId.toString().substring(5, 10));
        oird.setPhone(1 + userId.toString().substring(3, 13));
        list.add(oird);

        itemService.recordOrderItem(list);

        /*
        如果这里抛出异常，那么本地spring事务只会作用于本地方法调用，
        对于dubbo服务itemService.recordOrderItem(list); 并不会回滚
        那么就会出现分布式事务问题
         */
//        if(true)
//        	throw new OrderBizException(MsgCode.ERROR);

        return ResponseMessage.success(orderId.toString());
    }

    @Override
    public ResponseMessage<OrderEntity> getOrder(Long orderId) {
        return ResponseMessage.success(orderEntityMapper.selectByPrimaryKey(orderId));
    }

}
