package com.ronglian.kangrui.saas.research.sci.consts;


/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-06 18:25
 **/
public class ResearchConsts {

    // 顶级ID
    public static final int PARENT_ROOT_ID = -1 ;


    /*  删除标志(0：未删除  1：已删除)  */
    public static final int DELETED_NO = 0;
    public static final int DELETED_YES = 1;


    /*  项目标志(0: 单中心；1：多中心) */
    public static final int STUDY_FLAG_SINGLE_CENTER = 0 ;//0: 单中心
    public static final int STUDY_FLAG_MULTI_CENTER = 1 ;//1：多中心


    /* 项目状态: 1：创建中 2：待发布 3：已发布 4：已完成 */
    public static final int STUDY_STATUS_CREATING = 1 ;//项目状态(1：创建中)
    public static final int STUDY_STATUS_NOT_PUBLISHED = 2 ;//项目状态(2：待发布)
    public static final int STUDY_STATUS_PUBLISHED = 3 ;//项目状态(3：已发布)
    public static final int STUDY_STATUS_FINISHED = 4;//项目状态(4：已完成)

    public static final int CRF_TYPE_FORM = 1 ;//表单类型 1:CRF
    public static final int CRF_TYPE_TESTLETS = 2 ;//表单类型 2:题组


    public static final int GENERATE_TO_DB_NO = 0 ;//动态表单是否已创建（0：未创建）
    public static final int GENERATE_TO_DB_YES = 1 ;//动态表单是否已创建（1：已创建）


    public static final long CRF_FORM_PARENT_ROOT = -1 ;//CRF 根节点


    public static final int STUDY_TREE_LEVEL_STUDY = 1 ;// 树形结构-项目级别
    public static final int STUDY_TREE_LEVEL_CRF_FORM = 2 ;//树形结构-CRF 表单级别
    public static final int STUDY_TREE_LEVELE_CRF_TESTLETS = 3 ;//树形结构- 题组级别


    public static final int DISPLAY_YES = 0 ;// 是否显示(0：显示 )
    public static final int DISPLAY_NO = 1 ;// 是否显示(1：隐藏)


    public static final String STUDY_GROUP_NOT_DELETED_MSG = "该队列存在研究对象，不允许删除" ;
    public static final String STUDY_GROUP_DEL_SUC_MSG = "删除队列成功" ;


    public static final String CRF_FORM_NOT_DELETED_MSG = "CRF已经存在指标值不允许删除" ;
    public static final String CRF_FORM_DEL_SUC_MSG = "删除CRF成功" ;


    public static final String CRF_TESTLETS_NOT_DELETED_MSG = "题组已经存在指标值不允许删除" ;
    public static final String CRF_TESTLETS_DEL_SUC_MSG = "删除题组成功" ;


    public static final String STUDY_ROLE_NOT_DELETED_MSG = "项目角色不允许删除" ;
    public static final String STUDY_ROLE_DEL_SUC_MSG = "删除项目角色成功" ;


    public static final String DISEASE_NOT_DELETED_MSG = "疾病存在关联的项目，不允许删除" ;
    public static final String DISEASE_DEL_SUC_MSG = "删除疾病成功" ;


    public static final String STUDY_DEL_DEL_SUC_MSG = "删除项目成功" ;

    public static final String STUDY_OBJECT_DEL_SUC_MSG = "删除研究对象成功" ;


    public static final String FIELD_NOT_DELETED_MSG = "指标存在关联的项目，不允许删除" ;
    public static final String FIELD_DEL_SUC_MSG = "删除指标成功" ;


    public static final String SAVE_ERROR_CRF_FIELDS_INFO = "保存指标信息失败" ;
    public static final String SAVE_SUC_CRF_FIELDS_INFO = "保存指标信息成功" ;

    public static final String STUDY_NAME_EXISTS_REPEAT = "项目名称存在重复" ;


    /********* CRF表单状态  3：填写中包含(0: 已填完 1：未填完 2：未开始填)  4：待审核  5：已审核  6：待修改 ************/
    public static final int COLLECT_FLAG_WRITING = 3 ;// 3：填写中

    public static final int COLLECT_FLAG_FINISHED = 0 ;// 0: 已填完
    public static final int COLLECT_FLAG_NOT_FINISHED = 1 ;//  1：未填完
    public static final int COLLECT_FLAG_NOT_BEGIN = 2 ; //  2：未开始填
    public static final int COLLECT_FLAG_AUDITING = 4 ;// 4: 待审核
    public static final int COLLECT_FLAG_AUDITED = 5 ;// 5: 已审核
    public static final int COLLECT_FLAG_MODIDYING = 6; // 6：待修改



    /********* 是否允许操作(0：允许修改删除  1：禁止修改删除) ************/
    public static final int ALLOW_OPERATE_YES = 0 ;//0：允许修改删除
    public static final int ALLOW_OPERATE_NO = 1; //1：禁止修改删除


    /*********  层级(1：项目 2：角色) ************/
    public static final int LEVEL_STUDY = 1 ;//层级：1：项目
    public static final int LEVEL_ROLE = 2 ;//层级：2：角色


    /*********  CRF 表单点击不同的按钮（0: 保存按钮  1：提交按钮  2：通过按钮  3：不通过按钮）  ************/
    public static final int BUTTON_SAVE = 0 ; //0: 保存按钮
    public static final int BUTTON_SUBMIT = 1 ; //1：提交按钮
    public static final int BUTTON_PASS = 2 ; //2：通过按钮
    public static final int BUTTON_NOT_PASS = 3 ; //3：不通过按钮


    /*********  层级(1：若代表中心，2：则代表队列) ************/
    public static final int LEVEL_FIRST = 1 ;// 1 若代表中心，2 则代表队列
    public static final int LEVEL_TWO = 2;//1 若代表队列，2 则代表中心


    /*********  id 列 ************/
    public static  final  String ID_COLUMN = "id";

    /*********  全部是否空 标志 ************/
    public static final int ALL_EMPTY_FLAG_YES = 1 ;// 是否空（是）
    public static final int ALL_EMPTY_FLAG_NO = 0 ;//是否空（否）


    /*********  CRF 属性列(0代表全部) ************/
    public static final int CRF_PROPERTY_INIT = 0;//初始(1：对象、2：随访、3：访视、4：其它)

}
