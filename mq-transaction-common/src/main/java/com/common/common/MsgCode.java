package com.common.common;

import java.io.Serializable;

/**
 * 响应体返回状态码和响应信息
 *
 */
public class MsgCode implements Serializable {

    private int code;
    private String msg;

    public static final MsgCode SUCCESS = new MsgCode(100100, "成功");
    public static final MsgCode ERROR = new MsgCode(200100, "失败");

    // 总异常
    public static final MsgCode INSERT_RESULT_0 = new MsgCode(200101, "失败");


    // 用户服务状态码
    public static final MsgCode TOKEN_ILLEGAL = new MsgCode(2002001, "token非法");

    // 订单服务状态码
    public static final MsgCode ORDER_ERROR = new MsgCode(2003001, "订单异常");

    public MsgCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
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
}
