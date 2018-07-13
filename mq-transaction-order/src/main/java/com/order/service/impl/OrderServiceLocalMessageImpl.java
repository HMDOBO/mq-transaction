package com.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.common.common.ResponseMessage;
import com.common.domain.OrderItemRecordDO;
import com.common.entity.ItemEntity;
import com.order.entity.OrderEntity;
import com.order.service.MessageService;
import com.order.service.OrderCommonService;
import com.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单服务实现
 *
 * 本地消息服务，完成可靠消息最终一致性
 *
 * 为了更直观的测试可靠消息最终一致，现在把订单服务调用item服务的关联去掉，
 * dubbo暂时去掉，通过mq消息保证数据统一
 * 步骤：
 * 1. application.properties去掉dubbo配置
 * 2. com.order.service.impl.OrderServiceImpl类里面调用dubbo服务注掉
 *
 */
@Service("localMessageOrderService")
public class OrderServiceLocalMessageImpl implements OrderService {

    @Autowired
    private OrderCommonService orderCommonService;
    @Autowired
    private MessageService messageService;

    @Override
    public ResponseMessage<OrderEntity> getOrder(Long orderId) {
        return null;
    }

    /**
     * 可靠消息最终一致性：本地消息服务
     *
     * 消息存储与发送在本服务完成，数据库也同订单服务在一个库
     * 那么从订单生成到消息发送都会在一个spring事务中完成
     * 确保订单生成后，消息一定会发送出去；或者消息发送出去，订单也一定生成了
     * 这样保证了消息一定会投递
     *
     *
     * 1. 生成订单
     * 2. 消息存储
     * 3. 消息发送
     *
     * @param userId
     * @param itemId
     * @return
     */
    @Override
    public ResponseMessage<String> createOrder(Long userId, Long itemId) {
        // 查询item
        ItemEntity item = orderCommonService.getItemEntityById(itemId);

        // 生成订单
        Long orderId = orderCommonService.createOrderInfo(userId, item);

        // 生成消息
        List<OrderItemRecordDO> buyRecordList = orderCommonService.generateMessage(itemId, orderId);

        // 可靠消息存储
        Long messageId = messageService.saveLocalMessageToDB(buyRecordList);

        // 可靠消息发送，如果失败，还有消息重发机制
        messageService.sendLocalBuyRecordMessage(buyRecordList, messageId);

        // TODO 整个过程任何一步出现异常都不会影响数据最终一致性

        return ResponseMessage.success(orderId.toString());
    }

}
