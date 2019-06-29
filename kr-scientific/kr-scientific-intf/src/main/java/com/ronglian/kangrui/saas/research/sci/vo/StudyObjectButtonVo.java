package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudyObjectButtonVo {
    private Integer addEnableFlag;
    private Integer deleteEnableFlag;
    private Integer settingEnableFlag;
    public StudyObjectButtonVo(Integer addEnableFlag,Integer deleteEnableFlag,Integer settingEnableFlag){
        this.addEnableFlag= addEnableFlag;
        this.deleteEnableFlag = deleteEnableFlag;
        this.settingEnableFlag = settingEnableFlag;
    }
}
