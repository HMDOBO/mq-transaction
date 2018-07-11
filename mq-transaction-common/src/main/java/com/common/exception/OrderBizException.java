package com.common.exception;

import com.common.common.MsgCode;

public class OrderBizException extends BizException {

    public OrderBizException(MsgCode msgCode) {
        super(msgCode);
    }

    public static void main(String[] args) {
        throw new OrderBizException(MsgCode.ORDER_ERROR);
    }


}
