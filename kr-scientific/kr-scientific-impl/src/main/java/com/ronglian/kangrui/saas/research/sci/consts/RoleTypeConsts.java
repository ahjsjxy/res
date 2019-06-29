package com.ronglian.kangrui.saas.research.sci.consts;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-28 15:22
 **/
public class RoleTypeConsts {

    public static final String STUDY_ADMIN = "项目管理员" ;

    // 项目管理员-按钮权限
    public static final String[] STUDY_ADMIN_BUTTON_ARRAY = {"study_delete", "study_setting"} ;

    // 数据管理员-按钮权限
    public static final String[] STUDY_DATA_ADMIN_BUTTON_ARRAY = {"study_object_delete", "study_object_setting", "study_object_pass", "study_object_nopass"} ;

    // 数据录入员-按钮权限
    public static final String[] STUDY_DATA_INPUTER_BUTTON_ARRAY = {"study_object_add", "study_object_delete", "study_object_save", "study_object_submit"} ;

    // 数据分析员-按钮权限
    public static final String[] STUDY_DATA_ANALYSIS_BUTTON_ARRAY = {"study_analysis"} ;


    public enum RoleTypeEnum {
        STUDY_ADMIN("项目管理员", RoleTypeConsts.STUDY_ADMIN_BUTTON_ARRAY),
        STUDY_DATA_ADMIN("数据管理员",RoleTypeConsts.STUDY_DATA_ADMIN_BUTTON_ARRAY),
        STUDY_DATA_INPUTER("数据录入员",RoleTypeConsts.STUDY_DATA_INPUTER_BUTTON_ARRAY),
        STUDY_DATA_ANALYSIS("数据分析员",RoleTypeConsts.STUDY_DATA_ANALYSIS_BUTTON_ARRAY) ;

        private String name ;
        private String[] buttonArray ;

        private RoleTypeEnum(String name, String[] buttonArray) {
            this.name = name ;
            this.buttonArray = buttonArray ;
        }


        public static String[] getButtonArrayByName(String name) {
            for (RoleTypeEnum roleTypeEnum : RoleTypeEnum.values()) {
                if (name.equals(roleTypeEnum.getName())) {
                    return roleTypeEnum.getButtonArray()  ;
                }
            }
            return null ;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getButtonArray() {
            return buttonArray;
        }

        public void setButtonArray(String[] buttonArray) {
            this.buttonArray = buttonArray;
        }
    }

}
