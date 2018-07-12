package com.common.exception;

import com.common.common.MsgCode;

/**
 * 业务异常超类
 * <p>
 * 各个服务异常类要继承此类
 */
public abstract class BizException extends RuntimeException {

    protected int code;
    protected String msg;

    public BizException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BizException(MsgCode msgCode) {
        super(msgCode.getMsg());
        this.code = msgCode.getCode();
        this.msg = msgCode.getMsg();
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
