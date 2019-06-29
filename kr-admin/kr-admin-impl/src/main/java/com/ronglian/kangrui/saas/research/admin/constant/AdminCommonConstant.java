package com.ronglian.kangrui.saas.research.admin.constant;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-06-17 14:41
 */
public class AdminCommonConstant {
    public final static int ROOT = -1;
    public final static int DEFAULT_GROUP_TYPE = 0;
    public final static int GROUP_TYPE_SPECIAL_ADMIN = -1;//系统初始化角色组类型-1

    /**
     * 权限关联类型
     */
    public final static String AUTHORITY_TYPE_GROUP = "group";
    /**
     * 权限资源类型
     */
    public final static String RESOURCE_TYPE_MENU = "menu";
    public final static String RESOURCE_TYPE_BTN = "button";

    public final static String RESOURCE_REQUEST_METHOD_GET = "GET";
    public final static String RESOURCE_REQUEST_METHOD_PUT = "PUT";
    public final static String RESOURCE_REQUEST_METHOD_DELETE = "DELETE";
    public final static String RESOURCE_REQUEST_METHOD_POST = "POST";

    public final static String RESOURCE_ACTION_VISIT = "访问";

    public final static String BOOLEAN_NUMBER_FALSE = "0";

    public final static String BOOLEAN_NUMBER_TRUE = "1";

    /** 用户账户启用状态 1:启用 0:未启用  */
    public static final int USER_ENABLE_FLAG_YES = 1;// 1:启用
    public static final int USER_ENABLE_FLAG_NO = 0 ;// 0:未启用

    /** 用户账户审核状态 1:审核成功 0:审核失败  */
    public static final int USER_AUDIT_STATUS_SUCCESS = 1 ; // 1:审核成功
    public static final int USER_AUDIT_STATUS_FAILURE = 0 ;// 0:审核失败


    public static final String USER_PASSWORD_INIT_PASSWORD = "123456" ;//管理端初始化密码


    public static final int DELETED_NO = 0;//0：未删除
    public static final int DELETED_YES = 1;// 1: 已删除

}

