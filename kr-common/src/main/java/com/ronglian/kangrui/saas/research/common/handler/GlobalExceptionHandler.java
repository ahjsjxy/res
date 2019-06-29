package com.ronglian.kangrui.saas.research.common.handler;

import com.ronglian.kangrui.saas.research.common.constant.CommonConstants;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.exception.auth.ClientTokenException;
import com.ronglian.kangrui.saas.research.common.exception.auth.UserTokenException;
import com.ronglian.kangrui.saas.research.common.exception.biz.OperationException;
import com.ronglian.kangrui.saas.research.common.msg.BaseResponse;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.exception.BaseException;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by kr on 2017/9/8.
 */
@ControllerAdvice("com.ronglian.kangrui")
@ResponseBody
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(BaseException.class)
    public BaseResponse baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        logger.error(ex.getMessage(),ex);
        response.setStatus(500);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(500);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(CommonConstants.EX_OTHER_CODE, ex.getMessage());
    }
    
    
    @ExceptionHandler(AuthorizationException.class)
    public BaseResponse shiroAuthorizationExceptionHandler(HttpServletResponse response, AuthorizationException ex) {
        response.setStatus(403);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(403, "未授权访问");
    }
    
    @ExceptionHandler(OperationException.class)
    public ObjectRestResponse operationExceptionHandler(HttpServletResponse response, OperationException ex) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", RestCodeConstants.STATUS_CODE_OPERATION_RUNTIME);
        ret.put("msg", ex.getMessage());
        return new ObjectRestResponse().rel(Boolean.FALSE).data(ret);
    }

    @ExceptionHandler(ClientTokenException.class)
    public BaseResponse clientTokenExceptionHandler(HttpServletResponse response, ClientTokenException ex) {
        response.setStatus(403);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(UserTokenException.class)
    public BaseResponse userTokenExceptionHandler(HttpServletResponse response, UserTokenException ex) {
        response.setStatus(401);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(UncategorizedSQLException.class)
    public BaseResponse sqlExceptionHandler(HttpServletResponse response, UncategorizedSQLException ex) {
        response.setStatus(400);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(CommonConstants.EX_SQL_CODE, ex.getMessage());
    }
}
