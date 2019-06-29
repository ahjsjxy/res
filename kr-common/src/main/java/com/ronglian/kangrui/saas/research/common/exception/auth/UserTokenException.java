package com.ronglian.kangrui.saas.research.common.exception.auth;


import com.ronglian.kangrui.saas.research.common.constant.CommonConstants;
import com.ronglian.kangrui.saas.research.common.exception.BaseException;

/**
 * Created by ace on 2017/9/8.
 */
public class UserTokenException extends BaseException {
    public UserTokenException(String message) {
        super(message, CommonConstants.EX_USER_INVALID_CODE);
    }
}
