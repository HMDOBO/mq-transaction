package com.item.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.common.domain.BuyRecordMessage;
import com.common.domain.OrderItemRecordDO;
import com.common.enmus.MessageSendStatusEnum;
import com.common.utils.IdWorker;
import com.item.api.service.ItemService;
import com.item.entity.ItemUserRecordEntity;
import com.item.mapper.ItemUserRecordEntityMapper;
import com.item.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品购买记录服务
 *
 */
@org.springframework.stereotype.Service // 测试mq本地消息最终一致，不用dubbo
//@Service(
//        version = "1.0.0",
//        application = "${dubbo.application.id}",
//        protocol = "${dubbo.protocol.id}",
//        registry = "${dubbo.registry.id}"
//)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemUserRecordEntityMapper itemUserRecordEntityMapper;
    @Autowired
    private MessageService messageService;

    @Transactional
    @Override
    public void recordOrderItem(List<OrderItemRecordDO> list) {
        for(OrderItemRecordDO orderItemRecordDO: list) {
            ItemUserRecordEntity itemUserRecordEntity = new ItemUserRecordEntity();
            itemUserRecordEntity.setId(IdWorker.getId());
            convertItemUserRecord(orderItemRecordDO, itemUserRecordEntity);
            itemUserRecordEntityMapper.insertSelective(itemUserRecordEntity);
        }
    }

    @Override
    public void buyRecordAndConfirmMessageSuccess(BuyRecordMessage message) {
        Long messageId = message.getMessageId();
        List<OrderItemRecordDO> list = message.getList();
        String queueName = message.getQueueName();

        // 商品购买记录
        recordOrderItem(list);

        // 消息状态改为发送成功
        messageService.modifyDBMessageSendStatus(messageId,
                MessageSendStatusEnum.SUCCESS.name());

    }

    private void convertItemUserRecord(OrderItemRecordDO orderItemRecordDO, ItemUserRecordEntity itemUserRecordEntity) {
        itemUserRecordEntity.setItem_id(orderItemRecordDO.getItem_id());
        itemUserRecordEntity.setOrder_id(orderItemRecordDO.getOrder_id());
        itemUserRecordEntity.setPhone(orderItemRecordDO.getPhone());
        itemUserRecordEntity.setUsername(orderItemRecordDO.getUsername());
    }

}
