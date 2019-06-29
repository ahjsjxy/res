package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-05 17:45
 **/
@Data
public class FormDataVo implements Serializable {
    private Long objectId;

    private Map<String,List<RecordVo>> forms;

}
