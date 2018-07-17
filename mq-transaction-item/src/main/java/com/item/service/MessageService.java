package com.item.service;

import com.common.entity.MessageEntity;

public interface MessageService {

    void modifyDBMessageSendStatus(Long messageId, String messageStatus);
    
    MessageEntity getMessageInfo(Long messageId);

}
