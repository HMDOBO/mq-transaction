package com.common.domain;

import java.util.List;

public class BuyRecordMessage {

    private Long messageId;
    private List<OrderItemRecordDO> list;
    private String queueName;

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public List<OrderItemRecordDO> getList() {
        return list;
    }

    public void setList(List<OrderItemRecordDO> list) {
        this.list = list;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
