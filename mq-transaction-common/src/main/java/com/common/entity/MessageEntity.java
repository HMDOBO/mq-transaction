package com.common.entity;

import java.io.Serializable;
import java.util.Date;

public class MessageEntity implements Serializable {
    private Long id;

    private String queue_name;

    private String message_data_type;

    private Date create_time;

    private Date edit_time;

    private Integer message_send_times;

    private String areadly_dead;

    private String status;

    private String message_body;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQueue_name() {
        return queue_name;
    }

    public void setQueue_name(String queue_name) {
        this.queue_name = queue_name == null ? null : queue_name.trim();
    }

    public String getMessage_data_type() {
        return message_data_type;
    }

    public void setMessage_data_type(String message_data_type) {
        this.message_data_type = message_data_type == null ? null : message_data_type.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(Date edit_time) {
        this.edit_time = edit_time;
    }

    public Integer getMessage_send_times() {
        return message_send_times;
    }

    public void setMessage_send_times(Integer message_send_times) {
        this.message_send_times = message_send_times;
    }

    public String getAreadly_dead() {
        return areadly_dead;
    }

    public void setAreadly_dead(String areadly_dead) {
        this.areadly_dead = areadly_dead == null ? null : areadly_dead.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body == null ? null : message_body.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", queue_name=").append(queue_name);
        sb.append(", message_data_type=").append(message_data_type);
        sb.append(", create_time=").append(create_time);
        sb.append(", edit_time=").append(edit_time);
        sb.append(", message_send_times=").append(message_send_times);
        sb.append(", areadly_dead=").append(areadly_dead);
        sb.append(", status=").append(status);
        sb.append(", message_body=").append(message_body);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}