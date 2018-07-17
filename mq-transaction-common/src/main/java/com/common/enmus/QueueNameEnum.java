package com.common.enmus;

import com.common.common.Constants;

/**
 * 消息队列名称枚举
 */
public enum QueueNameEnum {

    LOCAL_BUY_RECORD_QUEUE(Constants.LOCAL_BUY_RECORD_QUEUE, "订单本地消息服务队列"),

    ONESELF_BUY_RECORD_QUEUE(Constants.ONESELF_BUY_RECORD_QUEUE, "订单独立消息服务队列");

    private String value;
    private String desc;

    QueueNameEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
