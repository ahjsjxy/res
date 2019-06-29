package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-05 17:45
 **/
@Data
public class RecordVo implements Serializable {
    private Long id;
    private Map<String,RecordDetailVo> fields  =  new HashMap<String,RecordDetailVo>();
    // private Map<String,Object> fields  =  new HashMap<String,Object>();
    private int operationType;

}
