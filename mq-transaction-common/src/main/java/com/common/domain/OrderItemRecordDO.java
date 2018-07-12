package com.common.domain;

import java.io.Serializable;

public class OrderItemRecordDO implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7887506828815894037L;
	
	private Long item_id;
    private Long order_id;
    private String username;
    private String phone;

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "OrderItemRecordDO{" +
                "item_id=" + item_id +
                ", order_id=" + order_id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
