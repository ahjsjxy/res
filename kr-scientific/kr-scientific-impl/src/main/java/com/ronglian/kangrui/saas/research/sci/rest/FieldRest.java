package com.ronglian.kangrui.saas.research.sci.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.sci.entity.Field;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.manager.FieldManager;
import com.ronglian.kangrui.saas.research.sci.vo.FieldConvertVo;
import com.ronglian.kangrui.saas.research.sci.vo.FieldVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-13 14:15
 **/
@RestController
@RequestMapping("field")
@Slf4j
public class FieldRest {

    @Autowired
    private FieldManager fieldManager ;


    @ApiOperation("值列表")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<FieldVo> pageList(@RequestParam(defaultValue = "10") int limit,
                                                 @RequestParam(defaultValue = "1") int page,
                                                 @RequestParam Long dictionaryId,
                                                 String name){
        Page<FieldVo> result = PageHelper.startPage(page, limit,true);
        name = (StringUtils.isNoneBlank(name)) ? name.trim() : null ;
        List<FieldVo> fieldVoList = fieldManager.getFieldListByDictId(dictionaryId, name) ;
        return new TableResultResponse<FieldVo>(result.getTotal(), fieldVoList);
    }



    @ApiOperation("依赖字段下拉列表")
    @RequestMapping(value = "/dependlist",method = RequestMethod.GET)
    public  List<FieldVo> dependlist(@RequestParam Long dictionaryId, Long fieldId, String name) {
        name = (StringUtils.isNoneBlank(name)) ? name.trim() : null ;
        return fieldManager.getValidFieldList(dictionaryId, fieldId, name) ;
    }



    @ApiOperation("保存值")
    @RequestMapping(value = "saveFieldInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveFieldInfo(@RequestBody FieldConvertVo fieldConvertVo) {
        Field field = fieldManager.saveFieldInfo(fieldConvertVo.getDictionaryId(), fieldConvertVo.getFieldVo()) ;
        return new ObjectRestResponse().rel(Boolean.TRUE).data(field) ;
    }



    @ApiOperation("删除值")
    @RequestMapping(value = "deleteFieldInfo",method = RequestMethod.POST)
    public ObjectRestResponse deleteFieldInfo(@RequestParam String objectIdStr) {
        if (StringUtils.isBlank(objectIdStr)) {
            log.info("objectIdStr为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }

        Msg msg = fieldManager.deleteFieldInfo(objectIdStr.trim()) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()).code(msg.getCode()) ;
    }


    @ApiOperation("值状态-修改")
    @RequestMapping(value = "updateFieldStatus",method = RequestMethod.POST)
    public ObjectRestResponse updateFieldStatus(@RequestParam Long fieldId, @RequestParam Integer status) {
        boolean flag = fieldManager.updateFieldStatus(fieldId, status) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("值-英文名称重复")
    @RequestMapping(value = "checkNameExists",method = RequestMethod.POST)
    public ObjectRestResponse checkNameExists(@RequestParam Long dictionaryId,
                                              @RequestParam String name,
                                              Long fieldId) {
        if (dictionaryId==null || StringUtils.isBlank(name)) {
            log.info("name为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_PARAM_NULL) ;
        }

        boolean flag = fieldManager.checkFieldNameExists(dictionaryId, name.trim(), fieldId) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("值-中文名称重复")
    @RequestMapping(value = "checkLabelExists",method = RequestMethod.POST)
    public ObjectRestResponse checkLabelExists(@RequestParam Long dictionaryId,
                                               @RequestParam String label,
                                               Long fieldId) {
        boolean flag = fieldManager.checkFieldLabelExists(dictionaryId, label.trim(), fieldId) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("编辑值")
    @RequestMapping(value = "/getFieldById",method = RequestMethod.GET)
    public ObjectRestResponse getFieldById(@RequestParam Long fieldId) {
        FieldVo fieldVo = fieldManager.getFieldById(fieldId) ;
        return new ObjectRestResponse().data(fieldVo) ;
    }


}
