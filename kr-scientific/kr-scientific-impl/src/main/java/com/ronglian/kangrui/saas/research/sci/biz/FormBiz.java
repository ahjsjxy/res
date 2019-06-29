package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.CrfDictField;
import com.ronglian.kangrui.saas.research.sci.mapper.FormMapper;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FormBiz extends BaseBiz<FormMapper,FormsVo> {

    @Autowired
    private FormMapper formMapper ;

    @Autowired
    private CrfBiz crfBiz ;




    /**
     * 根据study id 查询项目的crf配置
     * @param studyId
     * @return
     */
    public StudyTreeVo getDisplayFieldList(Long studyId) {
        StudyTreeVo studyTreeVo = formMapper.getStudyInfoById(studyId) ;

        int studyFieldNum = 0 ;
        List<CrfParentVo> crfParentVoList = formMapper.getCrfFormListByStudyId(studyId) ;//crf form
        if (crfParentVoList!=null && crfParentVoList.size()>0) {
            for (CrfParentVo crfParentVo : crfParentVoList){
                crfParentVo = this.getChildListAndCrfParentFieldNum(crfParentVo) ;
                studyFieldNum += crfParentVo.getFieldNum() ;
            }
        }
        studyTreeVo.setChildList(crfParentVoList);
        studyTreeVo.setFieldNum(studyFieldNum);

        return studyTreeVo ;
    }



    /**
     * crf 表单下的字段个数
     * @param crfParentVo
     * @return
     */
    public CrfParentVo getChildListAndCrfParentFieldNum(CrfParentVo crfParentVo) {
        int crfParentFieldNum = 0 ;

        List<CrfVo> crfVoList = formMapper.getCrfSecondListByFormId(crfParentVo.getId()) ;//二级题组
        if (crfVoList!=null && crfVoList.size()>0) {
            for (CrfVo crfVo : crfVoList) {
                crfVo = this.getChildListAndCrfFieldNum(crfVo) ;
                crfParentFieldNum += crfVo.getFieldNum() ;
            }
        }
        crfParentVo.setChildList(crfVoList);
        crfParentVo.setFieldNum(crfParentFieldNum);

        return crfParentVo ;
    }




    /**
     * crf 题组下的字段个数
     * @param crfVo
     * @return
     */
    private CrfVo getChildListAndCrfFieldNum(CrfVo crfVo) {
        int crfFieldNum = 0 ;

        List<CrfDictionaryVo> dictionaryVoList = formMapper.getDictListByCrfId(crfVo) ;//字典
        if (dictionaryVoList!=null && dictionaryVoList.size()>0) {
            for (CrfDictionaryVo crfDictionaryVo : dictionaryVoList) {
                crfDictionaryVo = this.getChildListAndFieldNum(crfDictionaryVo) ;
                crfFieldNum += crfDictionaryVo.getFieldNum() ;
            }
        }
        crfVo.setChildList(dictionaryVoList);
        crfVo.setFieldNum(crfFieldNum);

        return crfVo ;
    }




    /**
     * 字典下的字段个数
     * @param crfDictionaryVo
     * @return
     */
    private CrfDictionaryVo getChildListAndFieldNum(CrfDictionaryVo crfDictionaryVo) {
        List<CrfFieldVo> fieldVoList = formMapper.getFieldListByDictId(crfDictionaryVo) ;//字段
        crfDictionaryVo.setFields(fieldVoList);

        int fieldNum = (fieldVoList!=null && fieldVoList.size()>0) ? fieldVoList.size() : 0 ;
        crfDictionaryVo.setFieldNum(fieldNum);

        return crfDictionaryVo ;
    }




    /**
     * 保存设置显示字段
     * @param studyTreeVo
     * @return
     */
    public int saveDisplayFieldInfo(StudyTreeVo studyTreeVo) {
        int rowNum = 0;

        List<CrfParentVo> crfParentVoList = studyTreeVo.getChildList() ;
        if (crfParentVoList!=null && crfParentVoList.size()>0) {
            for (CrfParentVo crfParentVo : crfParentVoList) {
                List<CrfVo> crfVoList = crfParentVo.getChildList() ;
                if (crfVoList!=null && crfVoList.size()>0) {
                    for (CrfVo crfVo : crfVoList){
                        List<CrfDictionaryVo> crfDictionaryVoList = crfVo.getChildList() ;
                        if (crfDictionaryVoList!=null && crfDictionaryVoList.size()>0) {
                            for (CrfDictionaryVo crfDictionaryVo : crfDictionaryVoList) {
                                List<CrfFieldVo> fieldVoList = crfDictionaryVo.getFields() ;
                                if (fieldVoList!=null && fieldVoList.size()>0) {
                                    for (CrfFieldVo fieldVo : fieldVoList) {
                                        this.saveFieldInfo(fieldVo);
                                        rowNum++ ;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return rowNum ;
    }



    /**
     * 保存单个字段信息
     * @param fieldVo
     */
    private void saveFieldInfo(CrfFieldVo fieldVo) {
        CrfDictField crfDictField = new CrfDictField() ;
        crfDictField.setId(fieldVo.getId());
        crfDictField.setDisplay(fieldVo.getDisplay());
        //log.info("field:{}----crfDictField:{}", fieldVo.getDisplay(), crfDictField.getDisplay());
        crfDictField.setUpdateTime(new Date());
        formMapper.updateCrfDictFieldDisplay(crfDictField) ;
    }



    /**
     * 根据 form id和display 查询字段列表
     * @param formId
     * @return
     */
    private List<CrfFieldVo> getFieldsByFormId(Long formId, Long crfSecondId) {
        List<CrfFieldVo> fieldVoList = formMapper.getFieldsByFormId(formId, crfSecondId);
        return fieldVoList ;
    }



    /**
     * 根据 form id和display 查询字段列表
     * @param formId
     * @return
     */
    private List<CrfFieldVo> getDisplayFieldsByFormId(Long formId) {
        List<CrfFieldVo> fieldVoList = formMapper.getDisplayFieldsByFormId(formId);
        return fieldVoList ;
    }


    /**
     * 根据 crf form id  查询所有已生成字段到DB的 未删除字段列表
     * @param formId
     * @return
     */
    private List<CrfFieldVo> getNormalFieldsByFormId(Long formId) {
        List<CrfFieldVo> fieldVoList = formMapper.getNormalFieldsByFormId(formId);
        return fieldVoList ;
    }



    /**
     * 根据字段id 获取字段下拉选项
     * @param fieldId
     * @return
     */
    public List<FieldOptionVo> getFieldOptionListByFieldId(Long fieldId){
        return formMapper.getFieldOptionListByFieldId(fieldId);
    }




    /**
     * 根据study id 查询 研究对象 列头可展示的form列表
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getNormalFormConfigVoList(Long studyId) {
        List<FormConfigVo> formConfigVoList = formMapper.getFormListByStudyId(studyId) ;//form集合
        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                List<CrfFieldVo> fieldVoList = this.getNormalFieldsByFormId(formConfigVo.getId());
                if (fieldVoList != null && fieldVoList.size() > 0) {
                    for (CrfFieldVo fieldVo : fieldVoList) {
                        List<FieldOptionVo> fieldOptionVoList = formMapper.getFieldOptionListByFieldId(fieldVo.getFieldId());
                        fieldVo.setOptionVoList(fieldOptionVoList);
                    }
                }
                formConfigVo.setFieldVoList(fieldVoList);
            }
        }

        return formConfigVoList ;
    }




    /**
     * 根据study id 查询form config
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getDisplayFormConfigVoList(Long studyId) {
        List<FormConfigVo> formConfigVoList = formMapper.getFormListByStudyId(studyId) ;//form集合
        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                List<CrfFieldVo> fieldVoList = this.getDisplayFieldsByFormId(formConfigVo.getId());
                if (fieldVoList != null && fieldVoList.size() > 0) {
                    for (CrfFieldVo fieldVo : fieldVoList) {
                        List<FieldOptionVo> fieldOptionVoList = formMapper.getFieldOptionListByFieldId(fieldVo.getFieldId());
                        fieldVo.setOptionVoList(fieldOptionVoList);
                    }
                }
                formConfigVo.setFieldVoList(fieldVoList);
            }
        }

        // 过滤掉crf 下面没有字段的form 列表
        formConfigVoList = formConfigVoList.stream().filter(formConfigVo -> (formConfigVo.getFieldVoList()!=null
                && formConfigVo.getFieldVoList().size()>0)).collect(Collectors.toList());
        return formConfigVoList ;
    }




    /**
     * 根据study id 查询form config
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getFormConfigVoList(Long studyId) {
        List<FormConfigVo> formConfigVoList = formMapper.getFormListByStudyId(studyId) ;//form集合
        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                List<CrfFieldVo> fieldVoList = this.getFieldsByFormId(formConfigVo.getId(), null);
                if (fieldVoList != null && fieldVoList.size() > 0) {
                    for (CrfFieldVo fieldVo : fieldVoList) {
                        List<FieldOptionVo> fieldOptionVoList = formMapper.getFieldOptionListByFieldId(fieldVo.getFieldId());
                        fieldVo.setOptionVoList(fieldOptionVoList);
                    }
                }
                formConfigVo.setFieldVoList(fieldVoList);
            }
        }
        return formConfigVoList ;
    }




    /**
     * 根据study id 查询form config
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getFormConfigVoListByConfigId(Long studyId, Long crfSecondId) {
        List<FormConfigVo> formConfigVoList = formMapper.getFormListByStudyId(studyId) ;//form集合
        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                List<CrfFieldVo> tempFieldVoList = this.getFieldsByFormId(formConfigVo.getId(), crfSecondId);
                if (tempFieldVoList != null && tempFieldVoList.size() > 0) {
                    // 筛选已生成数据库字段的列
                    List<CrfFieldVo>  fieldVoList = tempFieldVoList.stream().filter(crfFieldVo -> (
                            crfFieldVo.getGenerateToDb()== ResearchConsts.GENERATE_TO_DB_YES && StringUtils.isNotBlank(crfFieldVo.getFieldName()))).collect(Collectors.toList());

                    for (CrfFieldVo fieldVo : fieldVoList) {
                        List<FieldOptionVo> fieldOptionVoList = formMapper.getFieldOptionListByFieldId(fieldVo.getFieldId());
                        fieldVo.setOptionVoList(fieldOptionVoList);
                    }

                    formConfigVo.setFieldVoList(fieldVoList);
                }
            }
        }
        return formConfigVoList ;
    }



    /**
     * 根据study id 查询form config
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getFormConfigVoList(Long studyId, Long crfSecondId) {
        List<FormConfigVo> formConfigVoList = formMapper.getFormListByStudyId(studyId) ;//form集合
        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                List<CrfFieldVo> fieldVoList = this.getFieldsByFormId(formConfigVo.getId(), crfSecondId);
                if (fieldVoList != null && fieldVoList.size() > 0) {
                    for (CrfFieldVo fieldVo : fieldVoList) {
                        List<FieldOptionVo> fieldOptionVoList = formMapper.getFieldOptionListByFieldId(fieldVo.getFieldId());
                        fieldVo.setOptionVoList(fieldOptionVoList);
                    }
                }
                formConfigVo.setFieldVoList(fieldVoList);
            }
        }
        return formConfigVoList ;
    }




    /**
     * 研究对象-点击单个Crf对应的题组树形
     * @param crfFormId
     * @return
     */
    public CrfParentVo getOneFormInfoTree(Long crfFormId) {
        CrfParentVo crfParentVo = null ;

        List<CrfParentVo> crfParentVoList = formMapper.getCrfFormListById(crfFormId) ;
        if (crfParentVoList!=null && crfParentVoList.size()>0) {
            crfParentVo = (CrfParentVo)crfParentVoList.get(0) ;
            List<CrfVo> crfVoList = formMapper.getCrfSecondListByFormId(crfFormId) ;//二级题组

            Integer fieldNum = 0 ;
            if (crfVoList!=null && crfVoList.size()>0) {
                for (CrfVo crfVo : crfVoList) {
                    CrfVo paramCrfVo = new CrfVo() ;
                    BeanUtils.copyProperties(crfVo, paramCrfVo);
                    paramCrfVo = this.getChildListAndCrfFieldNum(paramCrfVo) ;
                    crfVo.setFieldNum(paramCrfVo.getFieldNum());

                    fieldNum += paramCrfVo.getFieldNum() ;
                }
            }
            crfParentVo.setChildList(crfVoList);
            crfParentVo.setFieldNum(fieldNum);
        }

        return crfParentVo ;
    }




    /**
     * 根据当前的课题配置crf 列表
     * @param studyId
     * @return
     */
    public List<CrfParentVo> getCrfFormListByStudyId(Long studyId) {
        return formMapper.getCrfFormListByStudyId(studyId) ;
    }


    /**
     *  校验返回的列名中是否含有objectId，手动为当前的objectId 创建一条form数据
     * @param formsVo
     * @param objectId
     * @return
     */
    public boolean createFormAddNewRow(FormsVo formsVo,Long studyId, Long objectId) {
        boolean flag = Boolean.FALSE ;

        List<FormConfigVo> formConfigList = formsVo.getFormConfigs() ;
        if (formConfigList!=null && formConfigList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigList) {
                formConfigVo.setObjectId(objectId);
                List<Map> mapList = formMapper.getFormDataByObjectId(formConfigVo) ;

                boolean keyExist = this.checkInsertNewRow(mapList) ;
                if (!keyExist) {
                    this.insertOneRowFormData(formConfigVo) ;
                }
            }

            flag = Boolean.TRUE ;
        }
        return flag ;
    }




    /**
     * 校验返回的列名中是否含有objectId,有说明form 已经有一行
     * @param mapList
     * @return
     */
    private boolean checkInsertNewRow(List<Map> mapList) {
        boolean keyExist = Boolean.FALSE ;

        if (mapList!=null && mapList.size()>0) {
            for (Map map : mapList) {
                keyExist = (map.containsKey("objectId") && map.get("objectId")!=null) ;
                break;
            }
        }

        return keyExist ;
    }



    /**
     * 插入一条新的form数据
     * @param formConfigVo
     */
    public void insertOneRowFormData(FormConfigVo formConfigVo){
        formMapper.insertOneRowFormData(formConfigVo);
    }

    /**
     * 更新crf的状态
     * @param tableName
     * @param state
     * @param objectId
     */
    public void updateFormState(String tableName,Integer state,Long objectId){
        formMapper.updateFormState(tableName,state,objectId);
    }

    /**
     * 获取crf的状态
     * @param tableName
     * @param objectId
     * @return
     */
    public StatusVo selectStatus(String tableName,Long objectId) {
        return formMapper.selectStatus(tableName,objectId);
    }
}
