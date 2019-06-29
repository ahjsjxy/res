package com.ronglian.kangrui.saas.research.common.constant;

/**
 * Created by ace on 2017/8/23.
 */
public class RestCodeConstants {

    public static final int TOKEN_ERROR_CODE = 40101;
    public static final int TOKEN_FORBIDDEN_CODE = 40301;
    
    public static String STATUS_CODE_OPERATION_RUNTIME = "10901";//
    public static String STATUS_CODE_UNAUTHORIZED = "10401";//
    public static String STATUS_CODE_FORBIDDEN = "10403";//


    public static String STATUS_CODE_DATA_ID_NULL = "700";//主键ID为空
    public static String STATUS_CODE_DELETE_EXCEPTION = "701" ;// 删除异常
    public static String STATUS_CODE_DELETE_NOT_ALLOWED = "702" ;//不允许删除
    public static String STATUS_CODE_DELETE_ID_INVALID = "703" ;//主键ID无效


    public static String STATUS_CODE_SAVE_CHECK_REPEAT = "710" ; //保存时，校验存在重复
    public static String STATUS_CODE_SAVE_EXCEPTION = "711" ;//保存异常
    public static String STATUS_CODE_DATA_PARAM_NULL = "712" ;//参数为空

    public static String STATUS_CODE_OPERATOR_NO_AUTH = "720";//按钮是否有权限操作
}
