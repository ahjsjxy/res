package com.ronglian.kangrui.saas.research.common.exception.auth;


import com.ronglian.kangrui.saas.research.common.constant.CommonConstants;
import com.ronglian.kangrui.saas.research.common.exception.BaseException;

/**
 * Created by ace on 2017/9/10.
 */
public class ClientInvalidException extends BaseException {
    public ClientInvalidException(String message) {
        super(message, CommonConstants.EX_CLIENT_INVALID_CODE);
    }
}
