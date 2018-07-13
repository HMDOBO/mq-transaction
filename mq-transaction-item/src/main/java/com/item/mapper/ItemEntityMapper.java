package com.item.mapper;

import com.common.entity.ItemEntity;

public interface ItemEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemEntity record);

    int insertSelective(ItemEntity record);

    ItemEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemEntity record);

    int updateByPrimaryKey(ItemEntity record);
}