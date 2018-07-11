package com.common.common;

import java.io.Serializable;

/**
 * 接口响应体统一返回格式
 *
 * @param <T>
 */
public class ResponseMessage<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public static ResponseMessage result(MsgCode msgCode) {
        ResponseMessage responseMessage = getInstance();
        responseMessage.code = msgCode.getCode();
        responseMessage.msg = msgCode.getMsg();
        return responseMessage;
    }

    public static <T> ResponseMessage<T> success(T data) {
        ResponseMessage responseMessage = result(MsgCode.SUCCESS);
        responseMessage.setData(data);
        return responseMessage;
    }

    public static ResponseMessage error(String message) {
        ResponseMessage responseMessage = getInstance();
        responseMessage.setCode(MsgCode.ERROR.getCode());
        responseMessage.setMsg(message);
        return responseMessage;
    }

    public ResponseMessage() {
    }

    public static ResponseMessage getInstance() {
        return new ResponseMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
