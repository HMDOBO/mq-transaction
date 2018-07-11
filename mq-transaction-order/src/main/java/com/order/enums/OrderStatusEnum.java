package com.order.enums;

public enum OrderStatusEnum {

    // 1000待付款，2000已取消，3000已付款，4000已完成
    UNPAID(1000,"待付款"),CANCEL(2000,"已取消"),

    PAID(3000,"已付款"),COMPLETE(4000,"已完成");

    private int code;
    private String description;

    private OrderStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
