package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-18 14:51
 **/
@Data
public class CrfFormVo implements Serializable {
    private Long id;
    private String name;//名称(crf/题组)
    private String description;//描述
    private Long parentId;//父级id
    private Integer property;//crf/题组属性（1：对象、2：随访、3：访视、4：其它）
    private String tableName;//数据库表名
    private List<String> fieldNameList ;//题组下的字段已生成到库的列名集合
    private Integer generateToDb ;// 动态表单是否已创建（0：未创建  1：已创建）

    private Long studyId ;//studyId
}
