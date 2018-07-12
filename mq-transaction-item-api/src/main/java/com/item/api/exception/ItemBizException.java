package com.item.api.exception;

import com.common.common.MsgCode;
import com.common.exception.BizException;

/**
 * 商品服务异常
 * <p>
 * 因为要用到dubbo，基于dubbo异常处理机制
 * <p>
 * provider异常要想抛到customer中，就需要异常类放到api的服务中去
 */
public class ItemBizException extends BizException {

    public ItemBizException(MsgCode msgCode) {
        super(msgCode);
    }

}
