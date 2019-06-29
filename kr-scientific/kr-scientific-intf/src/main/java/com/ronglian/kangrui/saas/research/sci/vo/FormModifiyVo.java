package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-06-25 10:50
 **/
@Data
public class FormModifiyVo implements Serializable {
    private String formName ;//crf form DB名称
    private Integer arrayIndex ;//数据所在索引ID
    private String keyIdValue ;//保存后当前id 的值
}
