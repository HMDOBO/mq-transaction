package com.order.message.scheduled;

/**
 * 消息定时发送计划
 */
public interface MessageScheduled {

    /**
     * 处理消息状态为“发送中” 但已经超时的消息
     */
    void handleSendingTimeOutMessage();

}
