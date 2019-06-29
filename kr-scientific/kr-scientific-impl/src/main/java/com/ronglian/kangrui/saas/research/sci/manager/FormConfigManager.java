package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.*;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.StudyCrf;
import com.ronglian.kangrui.saas.research.sci.util.MysqlSqlUtil;
import com.ronglian.kangrui.saas.research.common.components.KeyGenerator;
import com.ronglian.kangrui.saas.research.common.entity.keygen.impl.IPKeyGenerator;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-07 13:51
 **/
@Service
@Slf4j
public class FormConfigManager {

    @Autowired
    private FormBiz formBiz ;

    @Autowired
    private CrfBiz crfBiz ;

    @Autowired
    private CrfDictFieldBiz crfDictFieldBiz ;

    @Autowired
    private StudyObjectManager studyObjectManager ;

    @Autowired
    private CrfDictFieldManager crfDictFieldManager ;

    @Autowired
    private StudyCrfBiz studyCrfBiz ;

    @Autowired
    private KeyGenerator keyGenerator;

    @Autowired
    private JdbcTemplateBiz jdbcTemplate;

    @Autowired
    private FormDataManager formDataManager ;


    private static  final  String tablePrefix = "forms_";
    private static  final  String fieldPrefix = "field_";



    /**
     * 根据study id 查询项目的crf配置
     * @param studyId
     * @return
     */
    public StudyTreeVo getDisplayFieldList(Long studyId) {
        return formBiz.getDisplayFieldList(studyId) ;
    }



    /**
     * crf 表单下的字段个数
     * @param crfParentVo
     * @return
     */
    public CrfParentVo getChildListAndCrfParentFieldNum(CrfParentVo crfParentVo) {
        return formBiz.getChildListAndCrfParentFieldNum(crfParentVo) ;
    }




    /**
     * 保存设置显示字段
     * @param studyTreeVo
     * @return
     */
    public int saveDisplayFieldInfo(StudyTreeVo studyTreeVo) {
        return formBiz.saveDisplayFieldInfo(studyTreeVo) ;
    }




    /**
     * 根据study id 查询form config
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getDisplayFormConfigVoList(Long studyId) {
        return formBiz.getDisplayFormConfigVoList(studyId) ;
    }




    /**
     * 根据study id 查询form config
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getFormConfigVoList(Long studyId) {
        return formBiz.getFormConfigVoList(studyId) ;
    }




    /**
     * 根据study id 查询form config
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getFormConfigVoList(Long studyId, Long crfSecondId) {
        return formBiz.getFormConfigVoList(studyId, crfSecondId) ;
    }



    /**
     * 根据study id 查询form config
     * @param studyId
     * @return
     */
    public List<FormConfigVo> getFormConfigVoListByConfigId(Long studyId, Long crfSecondId) {
        return formBiz.getFormConfigVoListByConfigId(studyId, crfSecondId) ;
    }




    /**
     *  根据study object id和 form id 查询某个单节点的form的配置
     * @param objectId
     * @param crfFormId
     * @return
     */
    public List<FormConfigVo> getOneFormConfig(Long objectId, Long crfFormId, Long crfSecondId) {
        List<FormConfigVo> filterOneList = null ;

        List<FormConfigVo> formConfigVoList = this.getOneFormConfigByConfigId(objectId, crfSecondId) ;
        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            filterOneList = formConfigVoList.stream().filter(formConfigVo -> formConfigVo.getId().longValue()==crfFormId.longValue()).collect(Collectors.toList());
            if (filterOneList!=null && filterOneList.size()>0) {
                for (FormConfigVo formConfigVo : filterOneList) {
                    formConfigVo.setObjectId(objectId);
                }
            }
        }

        return filterOneList ;
    }



    /**
     * 根据object获取当前研究对象的 form 配置列表
     * @param objectId
     * @return
     */
    public List<FormConfigVo> getOneFormConfigByConfigId(Long objectId, Long crfSecondId) {
        // 根据研究对象id 查询study object
        StudyObject studyObject = studyObjectManager.selectById(objectId) ;

        // 根据study id 查询 所有的form配置列表
        List<FormConfigVo> formConfigVoList = this.getFormConfigVoListByConfigId(studyObject.getStudyId(), crfSecondId) ;
        return formConfigVoList;
    }





    /**
     * 根据object获取当前研究对象的 form 配置列表
     * @param objectId
     * @return
     */
    public List<FormConfigVo> getFormConfigByConfigId(Long objectId, Long crfSecondId) {
        // 根据研究对象id 查询study object
        StudyObject studyObject = studyObjectManager.selectById(objectId) ;

        // 根据study id 查询 所有的form配置列表
        List<FormConfigVo> formConfigVoList = this.getFormConfigVoList(studyObject.getStudyId(), crfSecondId) ;
        return formConfigVoList;
    }





    /**
     * 研究对象-点击单个Crf对应的题组树形
     * @param crfFormId
     * @return
     */
    public CrfParentVo getOneFormInfoTree(Long crfFormId) {
        return formBiz.getOneFormInfoTree(crfFormId) ;
    }



    /**
     * 根据当前的课题配置crf 列表
     * @param studyId
     * @return
     */
    public List<CrfParentVo> getCrfFormListByStudyId(Long studyId) {
        return formBiz.getCrfFormListByStudyId(studyId) ;
    }



    /**
     * 项目-创建CRF到DB
     * @param studyId
     * @return
     */
    public Msg saveCrfFormGenerateToDb(Long studyId) {
        Msg msg = new Msg();
        msg.setSucFlag(Boolean.TRUE);


        // 1 : 创建CRF表单  2：更新CRF的table_name和generate_to_db  3: 更新 crf_dict_field 的 field_name和generate_to_db
        boolean checkFormToFieldValidByStudyId = crfDictFieldManager.checkFormToFieldValidByStudyId(studyId) ;
        if (checkFormToFieldValidByStudyId) {
            boolean flag = saveFormConfig(studyId) ;
            msg.setSucFlag(flag);
        } else {
            log.info("checkHasCrfFormGenerateToDb----false");
        }

        return msg ;
    }



    /**
     *   1: 创建CRF 动态表单
     *   2：更新CRF的table_name和generate_to_db
     *   3: 更新 crf_dict_field 的 field_name和generate_to_db
     *
     * @param studyId
     */
    @Transactional
    public boolean saveFormConfig(Long studyId){
        boolean flag = Boolean.FALSE ;

        // 查询 study下面crf form 列表
        List<CrfFormVo> crfFormVoList = crfBiz.getCrfFormListByStudyId(studyId) ;
        if (crfFormVoList!=null && crfFormVoList.size()>0) {
            int num = 0 ;
            for (CrfFormVo crfFormVo : crfFormVoList) {
                //初始化数据
                IPKeyGenerator ipKeyGenerator = keyGenerator.getIPKeyGenerator();
                crfFormVo = this.initCrfFormVo(crfFormVo, ipKeyGenerator) ;
                List<CrfFieldVo> crfFieldVoList = this.initCrfFieldVoList(crfFormVo, studyId, ipKeyGenerator) ;
                if (crfFieldVoList!=null && crfFieldVoList.size()>0) {
                    FormConfigVo formConfigVo = this.initFormConfigVo(crfFormVo, crfFieldVoList) ;

                    // 1:创建成功后保存表配置
                    boolean generateTableRes = this.generateTable(formConfigVo);
                    if(generateTableRes){
                        // 2：更新CRF的table_name和generate_to_db
                        crfBiz.updateCrfFormGenerateToDB(crfFormVo) ;

                        // 3: 更新 crf_dict_field 的 field_name和generate_to_db
                        crfDictFieldBiz.updateCrfFieldsGenerateToDB(crfFieldVoList) ;

                        num++ ;
                    }
                }
            }

            if (num>0) {
                flag = Boolean.TRUE ;
            }
        }

        return flag ;
    }



    /**
     *  动态生成表
     * @param formVo
     * @return
     */
    private boolean generateTable(FormConfigVo formVo){
        boolean res = false;
        try {
            String createTableSql = MysqlSqlUtil.generateCreateTableSql(formVo);
            jdbcTemplate.execute(createTableSql);
            res = true;
        }catch (Exception e){
            e.printStackTrace();
            log.error("create form table error,{}",e.getMessage());
        }
        return res;
    }




    /**
     * 初始化 initCrfFormVo
     * @param crfFormVo
     * @return
     */
    private CrfFormVo initCrfFormVo(CrfFormVo crfFormVo, IPKeyGenerator ipKeyGenerator) {
        String tableSuffix ="" + ipKeyGenerator.generateKey().longValue();
        crfFormVo.setTableName(tablePrefix+tableSuffix);
        return crfFormVo ;
    }



    /**
     * 初始化 initCrfFieldVoList
     * @param crfFormVo
     * @param studyId
     * @param ipKeyGenerator
     * @return
     */
    private List<CrfFieldVo> initCrfFieldVoList(CrfFormVo crfFormVo, Long studyId, IPKeyGenerator ipKeyGenerator) {
        CrfDictFieldVo temp = new CrfDictFieldVo() ;
        temp.setCrfFormId(crfFormVo.getId());
        temp.setStudyId(studyId);
        List<CrfFieldVo> crfFieldVoList = crfDictFieldBiz.getCrfAndFieldGenerateToDbNoList(temp) ;
        if (crfFieldVoList != null && crfFieldVoList.size()>0) {
            for (CrfFieldVo crfFieldVo : crfFieldVoList) {
                String fieldSuffix ="" + ipKeyGenerator.generateKey().longValue();
                crfFieldVo.setFieldName(fieldPrefix+fieldSuffix);
            }
        }

        return crfFieldVoList ;
    }



    /**
     * 初始化FormConfigVo
     * @param crfFormVo
     * @param crfFieldVoList
     * @return
     */
    private FormConfigVo initFormConfigVo(CrfFormVo crfFormVo, List<CrfFieldVo> crfFieldVoList) {
        FormConfigVo formConfigVo = new FormConfigVo() ;
        formConfigVo.setTableName(crfFormVo.getTableName());
        formConfigVo.setFieldVoList(crfFieldVoList);

        return formConfigVo ;
    }




    /**
     * Crf表单-创建字段到DB
     * @param crfFormId
     * @return
     */
    public Msg saveFieldsGenerateToDb(Long crfFormId) {
        Msg msg = new Msg();
        msg.setSucFlag(Boolean.TRUE);

        //  1：先更新动态表单的结构，添加几列  2: 更新 crf_dict_field 的 field_name和generate_to_db
        boolean checkFormToFieldValid = crfDictFieldManager.checkFormToFieldValid(crfFormId) ;
        if (checkFormToFieldValid){
            boolean flag = saveFormFieldConfig(crfFormId) ;
            msg.setSucFlag(flag);
        } else {
            log.info("checkFormToFieldValid------------false");
        }

        return msg ;
    }



    /**
     * 1：先更新动态表单的结构，添加几列
     * 2: 更新 crf_dict_field 的 field_name和generate_to_db
     *
     * @param crfFormId
     * @return
     */
    @Transactional
    public boolean saveFormFieldConfig(Long crfFormId) {
        boolean flag = Boolean.FALSE;

        FormConfigVo formConfigVo = new FormConfigVo() ;
        Crf crf = crfBiz.selectById(crfFormId) ;
        if (crf!=null && StringUtils.isNoneBlank(crf.getTableName())
                            && crf.getDeleted()== ResearchConsts.DELETED_NO) {
            formConfigVo.setTableName(crf.getTableName());
        }

        // 查询 CRF存在字段未创建到数据库列的
        int num = 0 ;
        List<CrfFieldVo> crfFieldVoList = crfDictFieldBiz.getCrfFieldList(crfFormId) ;
        if (crfFieldVoList!=null && crfFieldVoList.size()>0) {
            for (CrfFieldVo crfFieldVo : crfFieldVoList) {
                //初始化数据
                IPKeyGenerator ipKeyGenerator = keyGenerator.getIPKeyGenerator();
                String fieldSuffix ="" + ipKeyGenerator.generateKey().longValue();
                crfFieldVo.setFieldName(fieldPrefix+fieldSuffix);

                // 1: 先更新动态表单的结构，添加几列
                boolean insertTableColumnRes = insertTableColumn(crfFieldVo, formConfigVo) ;
                if (insertTableColumnRes) {
                    // 2: 更新 crf_dict_field 的 field_name和generate_to_db
                    crfDictFieldBiz.updateCrfFieldsGenerateToDB(crfFieldVo) ;
                }
                num++ ;
            }

            if (num>0) {
                flag = Boolean.TRUE ;
            }
        }

        return flag ;
    }




    /**
     *  先更新动态表单的结构，添加几列
     * @param fieldVo
     * @param formVo
     */
    public boolean insertTableColumn(CrfFieldVo fieldVo, FormConfigVo formVo) {
        boolean res = false;

        try {
            String addColumnSql = MysqlSqlUtil.generateAddColumnSql(formVo.getTableName(), fieldVo.getFieldName());
            jdbcTemplate.execute(addColumnSql);

            res = true;
        }catch (Exception e){
            e.printStackTrace();
            log.error("alter table add column error,{}",e.getMessage());
        }
        return res;
    }




    /**
     * 保存后，获取form相关信息
     *
     * @param formsVo
     * @param objectId
     * @return
     */
    public FormModifiyVo getFormModifiyInfo(FormsVo formsVo, Long objectId) {
        FormModifiyVo formModifiyVo = new FormModifiyVo() ;

        List<FormConfigVo> formConfigs = formsVo.getFormConfigs();
        formModifiyVo.setFormName(formConfigs.get(0).getTableName());
        formModifiyVo.setArrayIndex(0);

        StatusVo statusVo = formDataManager.selectStatus(formConfigs.get(0).getTableName(), objectId) ;
        formModifiyVo.setKeyIdValue(statusVo.getId()+"");

        return formModifiyVo ;

    }




    /**
     * 查询CRF以及所有字段都已经创建列
     * @param formsVo
     * @return
     */
    public boolean checkCrfConfigValid(FormsVo formsVo) {
        List<FormConfigVo> formConfigs = formsVo.getFormConfigs();

        // 查询CRF以及所有字段都已经创建列
        boolean checkCrfConfigValid = crfDictFieldManager.checkCrfConfigValid(formConfigs.get(0).getId()) ;
        return checkCrfConfigValid ;
    }




    /**
     * Crf表单-校验存在未删除的字段  1: CRF 已经创建表到DB  2：CRF存在字段未创建到数据库列的
     *
     * @param studyId
     * @return
     */
    public void saveFieldsGenerateToDbByStudyId(Long studyId) {
        List<StudyCrf> crfFormList = studyCrfBiz.getCrfListByStudyId(studyId) ;
        if (crfFormList!=null && crfFormList.size()>0) {
            for (StudyCrf temp : crfFormList) {
                boolean flag = crfDictFieldManager.checkHasFieldsGenerateToDb(temp.getCrfFormId()) ;
                if (flag) {
                    saveFormFieldConfig(temp.getCrfFormId()) ;
                }
            }
        }
    }


}
