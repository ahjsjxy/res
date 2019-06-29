package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-08 18:07
 **/
@Data
public class DynamicFieldsVo implements Serializable {
    private List<FormConfigVo> formConfigVoList ; //显示的列头
    private List<CrfParentVo> crfList ;//crf列头
}
