package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.biz.DiseaseBiz;
import com.ronglian.kangrui.saas.research.sci.biz.FormBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Dictionary;
import com.ronglian.kangrui.saas.research.sci.entity.Disease;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-15 20:43
 **/
@Service
@Slf4j
public class DiseaseManager {

    @Autowired
    private DiseaseBiz diseaseBiz;

    @Autowired
    private StudyBiz studyBiz ;

    @Autowired
    private FormBiz formBiz;

    @Autowired
    private FieldManager fieldManager ;

    @Autowired
    private DiseaseFieldManager diseaseFieldManager ;

    @Autowired
    private DictionaryManager dictionaryManager ;


    /***
     * 查询疾病列表
     * @param name
     * @return
     */
    public List<DiseaseVo> getDiseaseList(String name) {
        return diseaseBiz.getDiseaseList(name) ;
    }



    /**
     * 保存疾病
     * @param disease
     * @return
     */
    public Disease saveDiseaseInfo(Disease disease) {
        Msg msg = diseaseBiz.saveDiseaseInfo(disease) ;
        if (msg.getSucFlag()) {
            disease = (Disease)msg.getData() ;
        }
        return disease ;
    }




    /**
     * 根据disease id 编辑disease
     * @param diseaseId
     * @return
     */
    public Disease getDiseaseById(Long diseaseId) {
        return diseaseBiz.selectById(diseaseId) ;
    }




    /**
     * 校验疾病名称是否重复
     * @param name
     * @param diseaseId
     * @return
     */
    public boolean checkDiseaseNameExists(String name, Long diseaseId) {
        return diseaseBiz.checkDiseaseNameExists(name, diseaseId) ;
    }



    /**
     * 判断疾病ID是否有 关联的项目，1：存在未删除的项目，不允许删除；若不关联项目，则删除疾病
     * @param objectIdStr
     * @return
     */
    public Msg deleteDiseaseInfo(String objectIdStr) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            List<Long> objectIdList = Arrays.asList(objectIdStr.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(toList());
            if (objectIdList!=null && objectIdList.size()>0) {
                // 查询疾病ID集合是否有关联的 未删除的项目
                List<StudyVo> studyVoList = studyBiz.getStudyListByDiseaseIdList(objectIdList) ;
                if (studyVoList!=null && studyVoList.size()>0) {
                    msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_NOT_ALLOWED);
                    msg.setDesc(ResearchConsts.DISEASE_NOT_DELETED_MSG);
                } else {
                    // 删除疾病
                    msg = diseaseBiz.deleteDisease(objectIdStr) ;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());

            //删除异常
            msg.setCode(RestCodeConstants.STATUS_CODE_DELETE_EXCEPTION);
        }

        return msg ;
    }




    /**
     * 把某疾病置为 开启/关闭
     * @param diseaseId
     * @return
     */
    public Boolean updateDiseaseStatus(Long diseaseId, Integer status) {
        return diseaseBiz.updateDiseaseStatus(diseaseId, status) ;
    }


    /**
     * 根据字典id 和 疾病id 查询字段详情
     * @param diseaseId
     * @param dictId
     * @return
     */
    public List<DiseaseFieldVo> getFieldListByDiseaseId(Long diseaseId, Long dictId){
        List<DiseaseFieldVo> diseaseFieldVoList = new ArrayList<DiseaseFieldVo>() ;

        // 查询这个字典下面所有的 有效字段
        List<FieldVo> fieldVoList = fieldManager.getFieldListByDictId(dictId, null) ;

        // 字段列表
        if (fieldVoList!=null && fieldVoList.size()>0) {
            for (FieldVo fieldVo : fieldVoList) {
                DiseaseFieldVo diseaseFieldVo = new DiseaseFieldVo() ;
                BeanUtils.copyProperties(fieldVo, diseaseFieldVo);

                // 判断该字段是否被该疾病选中
                boolean isChecked = this.checkFieldSelectedByDiseaseId(diseaseId, dictId, fieldVo.getId()) ;
                diseaseFieldVo.setChecked(isChecked);

                // 该字段的所有父级id 集合
                List<Long> parentFieldIdList = this.getParentFieldIdList(fieldVo.getId()) ;
                diseaseFieldVo.setParentFieldList(parentFieldIdList);

                // 该字段的所有子级id 集合
                List<Long> childFieldIdList = this.getChildFieldIdList(fieldVo.getId()) ;
                diseaseFieldVo.setChildFieldList(childFieldIdList);

                diseaseFieldVoList.add(diseaseFieldVo) ;
            }
        }

        return diseaseFieldVoList ;
    }


    /**
     * 该字段的所有父级id 集合
     * @param fieldId
     * @return
     */
    private List<Long> getParentFieldIdList(Long fieldId) {
        List<FieldVo> parentFieldList = fieldManager.getParentListById(fieldId) ;

        List<Long> parentFieldIdList = new ArrayList<Long>() ;
        if (parentFieldList!=null && parentFieldList.size()>0) {
            parentFieldIdList = parentFieldList.stream().map(FieldVo::getParentId).collect(Collectors.toList());

            // 父级id 筛选掉 不含自己
            parentFieldIdList = parentFieldIdList.stream().filter(fieldTempId -> (fieldTempId.longValue()!=fieldId.longValue())).collect(Collectors.toList());
        }
        return parentFieldIdList ;
    }



    /**
     * 该字段的所有子级id 集合
     * @param fieldId
     * @return
     */
    private List<Long> getChildFieldIdList(Long fieldId) {
        List<FieldVo> childFieldList = fieldManager.getChildListById(fieldId) ;

        List<Long> childFieldIdList = new ArrayList<Long>() ;
        if (childFieldList!=null && childFieldList.size()>0) {
            childFieldIdList = childFieldList.stream().map(FieldVo::getId).collect(Collectors.toList());
        }
        return childFieldIdList ;
    }




    /**
     *  判断该字段是否被该疾病选中
     * @param diseaseId
     * @param dictId
     * @param fieldId
     * @return
     */
    private boolean checkFieldSelectedByDiseaseId(Long diseaseId, Long dictId, Long fieldId) {
        boolean isChecked = Boolean.FALSE ;

        // 查询这个疾病下面这个字典  选中的字段
        List<FieldVo> selectFieldList = diseaseBiz.getSelectFieldByDiseaseId(diseaseId, dictId, fieldId) ;
        isChecked = (selectFieldList!=null && selectFieldList.size()>0) ? Boolean.TRUE : Boolean.FALSE ;

        return isChecked ;
    }





    /**
     * 分配字段更新
     * @param diseaseId
     * @param dictId
     * @param diseaseFieldVoList
     * @return
     */
    @Transactional
    public boolean saveDiseaseField(Long diseaseId, Long dictId, List<DiseaseFieldVo> diseaseFieldVoList) {
        boolean flag = Boolean.FALSE ;

        // 1：删除disease_field，根据diseaseId和dictId
        diseaseFieldManager.deleteByDiseaseId(diseaseId, dictId);

        // 2：把选中的字段新增插入 disease_field，根据diseaseId和dictId
        if (diseaseFieldVoList!=null && diseaseFieldVoList.size()>0) {
            List<DiseaseFieldVo> selectFieldList = diseaseFieldVoList.stream().filter(diseaseFieldVo -> (diseaseFieldVo.isChecked()==Boolean.TRUE) ).collect(toList());
            for (DiseaseFieldVo diseaseFieldVo : selectFieldList) {
                diseaseFieldManager.addByDiseaseId(diseaseId, dictId, diseaseFieldVo.getId());
            }
        }

        flag = Boolean.TRUE ;
        return flag ;
    }



    /**
     * 获取某疾病下的某个字典的 字段结构展示
     * @param diseaseId
     * @param dictionaryId
     * @return
     */
    public FormsVo getOneDictionaryInfo(Long diseaseId, Long dictionaryId) {
        FormsVo formsVo = new FormsVo() ;

        List<FormConfigVo> formConfigVoList = new ArrayList<FormConfigVo>();
        // 初始化配置
        FormConfigVo formConfigVo = new FormConfigVo() ;

        // 字典信息
        Dictionary dictionary = dictionaryManager.getDictionaryById(dictionaryId) ;
        formConfigVo.setId(dictionary.getId());
        formConfigVo.setName(dictionary.getName());
        formConfigVo.setTableName(dictionary.getCode());

        List<CrfFieldVo> fieldList = new ArrayList<CrfFieldVo>() ;
        // 该疾病 某字典下面的字段
        List<FieldVo> fieldVoList = diseaseBiz.getSelectFieldByDiseaseId(diseaseId, dictionaryId, null) ;
        if (fieldVoList!=null && fieldVoList.size()>0) {
            for (FieldVo fieldVo : fieldVoList) {
                CrfFieldVo crfFieldVo = new CrfFieldVo() ;
                BeanUtils.copyProperties(fieldVo, crfFieldVo);
                crfFieldVo.setId(null);
                crfFieldVo.setFieldId(fieldVo.getId());
                crfFieldVo.setFieldName(fieldVo.getName());
                crfFieldVo.setDictionaryCode(dictionary.getCode());
                crfFieldVo.setDisplay(ResearchConsts.DELETED_NO);//是否显示(0：显示；1：隐藏)

                // 下拉选项option
                List<FieldOptionVo> fieldOptionVoList = formBiz.getFieldOptionListByFieldId(fieldVo.getId());
                crfFieldVo.setOptionVoList(fieldOptionVoList);
                fieldList.add(crfFieldVo) ;
            }
        }
        formConfigVo.setFieldVoList(fieldList);
        formConfigVoList.add(formConfigVo) ;
        formsVo.setFormConfigs(formConfigVoList);


        // 组装 data 节点 （该段代码仅是为了前端适用动态表单组件， 无实际业务含义）
        FormDataVo dataVo = new FormDataVo() ;
        Map<String,List<RecordVo>> forms = new HashMap<String,List<RecordVo>>() ;
        forms.put(dictionary.getCode(), new ArrayList<RecordVo>()) ;
        dataVo.setForms(forms);
        formsVo.setData(dataVo);

        return formsVo ;
    }


    /**
     * 判断项目下是否关联疾病，如果关联疾病，则只显示疾病选中的字典下的字段
     * @param diseaseId
     * @param dictionaryId
     * @return
     */
    public List<DiseaseFieldVo> getFieldListByStudyId(Long diseaseId, Long dictionaryId) {
        List<DiseaseFieldVo> diseaseFieldVoList = new ArrayList<DiseaseFieldVo>() ;

        // 查询疾病下某字典  选中的字段
        List<FieldVo> selectFieldList = diseaseBiz.getSelectFieldByDiseaseId(diseaseId, dictionaryId, null) ;
        if (selectFieldList!=null && selectFieldList.size()>0) {
            for (FieldVo fieldVo : selectFieldList) {
                DiseaseFieldVo diseaseFieldVo = new DiseaseFieldVo() ;
                BeanUtils.copyProperties(fieldVo, diseaseFieldVo);

                // 该字段的所有父级id 集合
                List<Long> parentFieldIdList = this.getParentFieldIdList(fieldVo.getId()) ;
                diseaseFieldVo.setParentFieldList(parentFieldIdList);

                // 该字段的所有子级id 集合
                List<Long> childFieldIdList = this.getChildFieldIdList(fieldVo.getId()) ;
                diseaseFieldVo.setChildFieldList(childFieldIdList);

                diseaseFieldVoList.add(diseaseFieldVo) ;
            }
        }

        return diseaseFieldVoList ;
    }


    /**
     * 若项目没有选择疾病，则显示所有的字典下的字段。并通过题组属性过滤字典属性
     * @param dictId
     * @return
     */
    public List<DiseaseFieldVo> getFieldListByDictId(Long dictId) {
        List<DiseaseFieldVo> diseaseFieldVoList = new ArrayList<DiseaseFieldVo>();

        // 查询这个字典下面所有的 有效字段
        List<FieldVo> fieldVoList = fieldManager.getFieldListByDictId(dictId, null);
        if (fieldVoList!=null && fieldVoList.size()>0) {
            for (FieldVo fieldVo : fieldVoList){
                DiseaseFieldVo diseaseFieldVo = new DiseaseFieldVo() ;
                BeanUtils.copyProperties(fieldVo, diseaseFieldVo);

                // 该字段的所有父级id 集合
                List<Long> parentFieldIdList = this.getParentFieldIdList(fieldVo.getId()) ;
                diseaseFieldVo.setParentFieldList(parentFieldIdList);

                // 该字段的所有子级id 集合
                List<Long> childFieldIdList = this.getChildFieldIdList(fieldVo.getId()) ;
                diseaseFieldVo.setChildFieldList(childFieldIdList);

                diseaseFieldVoList.add(diseaseFieldVo) ;
            }
        }

        return diseaseFieldVoList ;
    }



}
