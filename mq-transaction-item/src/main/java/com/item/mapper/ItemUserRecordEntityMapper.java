package com.item.mapper;

import com.item.entity.ItemUserRecordEntity;

public interface ItemUserRecordEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ItemUserRecordEntity record);

    int insertSelective(ItemUserRecordEntity record);

    ItemUserRecordEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ItemUserRecordEntity record);

    int updateByPrimaryKey(ItemUserRecordEntity record);
}