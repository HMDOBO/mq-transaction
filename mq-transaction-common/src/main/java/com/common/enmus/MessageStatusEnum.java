package com.common.enmus;

public enum MessageStatusEnum {

    NODEAD("未死亡"), DEAD("死亡");

    private String desc;

    private MessageStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
