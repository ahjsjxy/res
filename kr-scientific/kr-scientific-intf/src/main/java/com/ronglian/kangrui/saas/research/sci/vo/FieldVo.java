package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-13 14:40
 **/
@Data
public class FieldVo implements Serializable {
    private Long id;
    private String code ;
    private String label;
    private String name;
    private String description;//描述
    private Integer fieldType;//1：单选题  2：多选题  3：填空  4：日期
    private Integer status ;//0：开启  1：关闭
    private Long parentId ;//依赖字段ID

    private Integer dataType;//1:单选; 2:下拉; 4:文本; 5:整数; 6:小数; 7:日期_年月日; 8:日期_年月; 9:时间_时分秒; 10:日期时间_年月日时分秒
    private String required;//是否必填（0：是；1：否）
    private String unit;//单位
    private Long minValue;//最小值
    private Long maxValue;//最大值
    private String defaultValue;// 默认值
    private Integer deleted;//是否删除(0：未删除  1：删除)
    private boolean disabled;//是否可用(true:可用，false:不可用)

    private List<FieldOptionVo> fieldOptionVoList ;//列的下拉选项

    private Long dictionaryId ;//二级字典ID
    private String dictionaryCode;


}
