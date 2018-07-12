package com.order.exception;

import com.common.common.MsgCode;
import com.common.exception.BizException;

/**
 * 订单服务异常
 *
 */
public class OrderBizException extends BizException {

    public OrderBizException(MsgCode msgCode) {
        super(msgCode);
    }

    public static void main(String[] args) {
        throw new OrderBizException(MsgCode.ORDER_ERROR);
    }

}
