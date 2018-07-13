package com.item.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.common.domain.OrderItemRecordDO;
import com.common.utils.IdWorker;
import com.item.api.service.ItemService;
import com.item.entity.ItemUserRecordEntity;
import com.item.mapper.ItemUserRecordEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//@Service
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemUserRecordEntityMapper itemUserRecordEntityMapper;

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

    private void convertItemUserRecord(OrderItemRecordDO orderItemRecordDO, ItemUserRecordEntity itemUserRecordEntity) {
        itemUserRecordEntity.setItem_id(orderItemRecordDO.getItem_id());
        itemUserRecordEntity.setOrder_id(orderItemRecordDO.getOrder_id());
        itemUserRecordEntity.setPhone(orderItemRecordDO.getPhone());
        itemUserRecordEntity.setUsername(orderItemRecordDO.getUsername());
    }

}
