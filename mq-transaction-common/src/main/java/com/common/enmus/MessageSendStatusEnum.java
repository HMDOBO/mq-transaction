package com.common.enmus;

public enum MessageSendStatusEnum {

    SENDING("等待发送"),
    SUCCESS("发送成功"),
    FAIL("失败");

    private String desc;

    MessageSendStatusEnum(String desc) {
        this.desc = desc;
    }
}
