package com.ronglian.kangrui.saas.research.common.msg.auth;

import com.ronglian.kangrui.saas.research.common.msg.BaseResponse;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;

/**
 * Created by ace on 2017/8/25.
 */
public class TokenForbiddenResponse extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
