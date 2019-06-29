package com.ronglian.kangrui.saas.research.sci.vo;

import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * form全节点信息
 *
 * @author lanyan
 * @create 2019-03-05 10:58
 **/
@Data
public class FormsVo implements Serializable {

    private Long               objectId;
    private List<FormConfigVo> formConfigs;
    private FormDataVo         data;
    private List<HospCenterVo> centerList;
    private Long               centerId;

    private int status;

    //五个按钮权限标记(0：无权限  1：有权限)
    private Integer saveEnableFlag;//保存按钮
    private Integer commitEnableFlag;//提交按钮
    private Integer cancelEnableFlag=1;//取消按钮
    private Integer passEnableFlag;//通过按钮
    private Integer unpassEnableFlag;//不通过按钮


}
