package com.ronglian.kangrui.saas.research.common.exception.auth;


import com.ronglian.kangrui.saas.research.common.constant.CommonConstants;
import com.ronglian.kangrui.saas.research.common.exception.BaseException;

/**
 * Created by ace on 2017/9/12.
 */
public class ClientForbiddenException extends BaseException {
    public ClientForbiddenException(String message) {
        super(message, CommonConstants.EX_CLIENT_FORBIDDEN_CODE);
    }

}
