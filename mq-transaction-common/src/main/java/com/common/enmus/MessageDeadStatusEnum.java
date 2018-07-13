package com.common.enmus;

public enum MessageDeadStatusEnum {

    NODEAD("未死亡"), DEAD("死亡");

    private String desc;

    private MessageDeadStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
