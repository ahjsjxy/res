package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * crf 关联的字段
 *
 * @author lanyan
 * @create 2019-03-05 11:12
 **/
@Data
public class CrfFieldVo implements Serializable {
    private Long id; //crf_dict_field表的主键ID
    private Long fieldId ;//field表的主键ID
    private String dictionaryCode;//关联的二级字典code
    private String label;//中文名称
    private String name;//英文名称
    private Integer fieldType;//1：单选题  2：多选题  3：填空  4：日期
    private Integer dataType;// 数据类型(1:单选; 2:下拉; 4:文本; 5:整数; 6:小数; 7:日期_年月日; 8:日期_年月; 9:时间_时分秒; 10:日期时间_年月日时分秒)
    private String required;//是否必填（0：是；1：否）
    private String fieldName;//字段DB名称
    private String unit;//单位
    private Long minValue;//最小值
    private Long maxValue;//最大值
    private String description;//字段描述
    private String defaultValue;//默认值
    private Integer display;//是否显示(0：显示；1：隐藏)
    private boolean disabled ;//是否可用(true：可用，false：不可用)
    private Integer generateToDb ; // 动态表单字段是否创建（0：未创建  1：已创建）
    private List<FieldOptionVo> optionVoList ;//字段的下拉选项(题目类型为 1：单选题  2：多选题)

}
