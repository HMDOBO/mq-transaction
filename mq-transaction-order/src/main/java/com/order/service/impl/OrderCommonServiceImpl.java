package com.order.service.impl;

import com.common.common.MsgCode;
import com.common.domain.OrderItemRecordDO;
import com.common.entity.ItemEntity;
import com.common.utils.IdWorker;
import com.order.entity.OrderEntity;
import com.order.enums.OrderStatusEnum;
import com.order.exception.OrderBizException;
import com.order.mapper.OrderEntityMapper;
import com.order.service.OrderCommonService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单服务公共实现
 */
@Service
public class OrderCommonServiceImpl implements OrderCommonService {

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Override
    public ItemEntity getItemEntityById(Long itemId) {
        ItemEntity item = new ItemEntity();
        item.setId(itemId);
        item.setName("iPhone X");
        item.setPrice(888800L);
        item.setImg("https://www.baidu.com/img/bd_logo1.png");
        item.setStock(200);
        return item;
    }

    @Override
    public Long createOrderInfo(Long userId, ItemEntity item) {
        OrderEntity order = new OrderEntity();
        long orderId = IdWorker.getId();

        order.setId(orderId);
        order.setUser_id(userId);
        order.setItem_id(item.getId());
        order.setItem_name(item.getName());
        order.setItem_price(item.getPrice());
        order.setItem_count(1);
        order.setTotal_price(order.getItem_count() * item.getPrice());
        order.setStatus(OrderStatusEnum.UNPAID.getCode());

        if (orderEntityMapper.insertSelective(order) <= 0)
            throw new OrderBizException(MsgCode.INSERT_RESULT_0);

        return orderId;
    }

    @Override
    public List<OrderItemRecordDO> generateMessage(Long itemId, Long orderId) {
        List<OrderItemRecordDO> list = new ArrayList<>();
        for (int i = 0; i < 3; i ++) {
            OrderItemRecordDO oird = new OrderItemRecordDO();
            oird.setItem_id(itemId);
            oird.setOrder_id(orderId);
            oird.setPhone(RandomUtils.nextInt(50000, 100000) + "");
            oird.setUsername(RandomUtils.nextInt(50000, 100000) + "");
            list.add(oird);
        }
        return list;
    }
}
