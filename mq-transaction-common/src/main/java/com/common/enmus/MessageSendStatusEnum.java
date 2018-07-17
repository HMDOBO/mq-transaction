package com.common.enmus;

/**
 * 消息状态枚举
 */
public enum MessageSendStatusEnum {

    SENDING("等待发送"),
    SUCCESS("发送成功"),
    FAILURE("发送失败");

    private String desc;

    MessageSendStatusEnum(String desc) {
        this.desc = desc;
    }
}
