package com.ronglian.kangrui.saas.research.sci.rest;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.sci.entity.Disease;
import com.ronglian.kangrui.saas.research.sci.manager.DiseaseManager;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.msg.TableResultResponse;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.manager.DictionaryManager;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 疾病管理
 *
 * @author lanyan
 * @create 2019-03-15 20:42
 **/
@RestController
@RequestMapping("disease")
@Slf4j
public class DiseaseRest {

    @Autowired
    private DiseaseManager diseaseManager ;

    @Autowired
    private DictionaryManager dictionaryManager ;


    @ApiOperation("疾病列表")
    @RequestMapping(value = "/pageList",method = RequestMethod.GET)
    public TableResultResponse<DiseaseVo> pageList(@RequestParam(defaultValue = "10") int limit,
                                                   @RequestParam(defaultValue = "1") int page,
                                                   String name){
        Page<DiseaseVo> result = PageHelper.startPage(page, limit,true);
        name = (StringUtils.isNoneBlank(name)) ? name.trim() : null ;
        List<DiseaseVo> diseaseVoList = diseaseManager.getDiseaseList(name) ;
        return new TableResultResponse<DiseaseVo>(result.getTotal(), diseaseVoList);
    }


    @ApiOperation("保存疾病")
    @RequestMapping(value = "/saveDiseaseInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveDiseaseInfo(@RequestBody Disease disease) {
        disease = diseaseManager.saveDiseaseInfo(disease) ;
        return new ObjectRestResponse().data(disease) ;
    }



    @ApiOperation("编辑疾病")
    @RequestMapping(value = "/getDiseaseById",method = RequestMethod.GET)
    public ObjectRestResponse getDiseaseById(@RequestParam Long diseaseId) {
        Disease disease = diseaseManager.getDiseaseById(diseaseId) ;
        return new ObjectRestResponse().data(disease) ;
    }



    @ApiOperation("疾病名称重复")
    @RequestMapping(value = "/checkNameExists",method = RequestMethod.POST)
    public ObjectRestResponse checkNameExists(@RequestParam String name,
                                              Long diseaseId) {
        if (StringUtils.isBlank(name)) {
            log.info("name为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_PARAM_NULL) ;
        }

        boolean flag = diseaseManager.checkDiseaseNameExists(name.trim(), diseaseId) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("删除疾病")
    @RequestMapping(value = "/deleteDisease",method = RequestMethod.POST)
    public ObjectRestResponse deleteDisease(@RequestParam String objectIdStr) {
        if (StringUtils.isBlank(objectIdStr)) {
            log.info("objectIdStr为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }

        Msg msg = diseaseManager.deleteDiseaseInfo(objectIdStr.trim()) ;
        return new ObjectRestResponse().rel(msg.getSucFlag()).data(msg.getDesc()).code(msg.getCode()) ;
    }



    @ApiOperation("疾病状态-修改")
    @RequestMapping(value = "/updateFieldStatus",method = RequestMethod.POST)
    public ObjectRestResponse updateFieldStatus(@RequestParam Long diseaseId, @RequestParam Integer status) {
        if (diseaseId==null || status==null) {
            log.info("diseaseId为空或status为空=============");
            return new ObjectRestResponse().rel(Boolean.FALSE).code(RestCodeConstants.STATUS_CODE_DATA_ID_NULL) ;
        }

        boolean flag = diseaseManager.updateDiseaseStatus(diseaseId, status) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("疾病-字典列表")
    @RequestMapping(value = "/diseasePageList",method = RequestMethod.GET)
    public TableResultResponse<DictionaryVo> diseasePageList(@RequestParam(defaultValue = "10") int limit,
                                                             @RequestParam(defaultValue = "1") int page,
                                                             @RequestParam Long diseaseId,
                                                             String name){
        Page<DictionaryVo> result = PageHelper.startPage(page, limit,true);
        List<DictionaryVo> dictionaryVoList = dictionaryManager.getDictionaryByDiseaseId(diseaseId, name) ;
        return new TableResultResponse<DictionaryVo>(result.getTotal(), dictionaryVoList);
    }




    @ApiOperation("疾病-字段列表")
    @RequestMapping(value = "/getFieldList",method = RequestMethod.GET)
    public List<DiseaseFieldVo> getFieldList(@RequestParam Long diseaseId, @RequestParam Long dictionaryId) {
        return diseaseManager.getFieldListByDiseaseId(diseaseId, dictionaryId) ;
    }




    @ApiOperation("保存疾病字段")
    @RequestMapping(value = "/saveDiseaseFieldInfo",method = RequestMethod.POST)
    public ObjectRestResponse saveDiseaseFieldInfo(@RequestBody DiseaseFieldConvertVo diseaseFieldConvertVo) {
        boolean flag = diseaseManager.saveDiseaseField(diseaseFieldConvertVo.getDiseaseId(),
                            diseaseFieldConvertVo.getDictionaryId(), diseaseFieldConvertVo.getDiseaseFieldVoList()) ;
        return new ObjectRestResponse().rel(flag) ;
    }



    @ApiOperation("疾病-字典预览")
    @RequestMapping(value = "/getOneDictionaryInfo",method = RequestMethod.GET)
    public FormsVo getOneDictionaryInfo(@RequestParam Long diseaseId, @RequestParam Long dictionaryId) {
        FormsVo formsVo = diseaseManager.getOneDictionaryInfo(diseaseId, dictionaryId) ;
        return formsVo ;
    }



    @ApiOperation("疾病列表")
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<DiseaseVo> list(){
        List<DiseaseVo> diseaseVoList = diseaseManager.getDiseaseList(null) ;
        return diseaseVoList ;
    }
}
