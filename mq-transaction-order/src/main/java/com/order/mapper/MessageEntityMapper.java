package com.order.mapper;

import com.common.entity.MessageEntity;

import java.util.List;

public interface MessageEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MessageEntity record);

    int insertSelective(MessageEntity record);

    MessageEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MessageEntity record);

    int updateByPrimaryKeyWithBLOBs(MessageEntity record);

    int updateByPrimaryKey(MessageEntity record);

    List<MessageEntity> selectByParam(MessageEntity record);
}