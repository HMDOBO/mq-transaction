package com.item.service.impl;

import com.common.common.MsgCode;
import com.common.entity.MessageEntity;
import com.item.api.exception.ItemBizException;
import com.item.mapper.MessageEntityMapper;
import com.item.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageEntityMapper messageEntityMapper;

    @Override
    public void modifyDBMessageSendStatus(Long messageId, String messageStatus) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(messageId);
        messageEntity.setStatus(messageStatus);

        if (messageEntityMapper.updateByPrimaryKeySelective(messageEntity) <= 0)
            throw new ItemBizException(MsgCode.UPDATE_RESULT_0);
    }

	@Override
	public MessageEntity getMessageInfo(Long messageId) {
		return messageEntityMapper.selectByPrimaryKey(messageId);
	}
}
