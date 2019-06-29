package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-13 13:50
 **/
@Data
public class DictionaryVo implements Serializable {
    private Long id;
    private String code;
    private String name;
    private String parentName;
    private Integer property; //字典属性 (1：对象、2：随访、3：访视、4：其它、0：初始)
    private Integer fieldNum ;//字段数
    private Integer selectFieldNum ; //选中字段数


    private Long diseaseId; //疾病id
    private Long crfId ;//题组id


}
