package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.consts.OperationConstant;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.util.MysqlSqlUtil;
import com.ronglian.kangrui.saas.research.sci.biz.FormBiz;
import com.ronglian.kangrui.saas.research.sci.biz.JdbcTemplateBiz;
import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-06 16:08
 **/
@Service
@Slf4j
public class FormDataManager {

    @Autowired
    private JdbcTemplateBiz jdbcTemplateBiz;

    @Autowired
    private FormBiz formBiz ;

    @Autowired
    private StudyObjectManager studyObjectManager ;

    @Autowired
    private FormConfigManager formConfigManager ;



    /**
     * 获取多个form 数据
     * @param formVos
     * @param objectId
     * @return
     */
    public FormDataVo getFormData(List<FormConfigVo> formVos, Long objectId) {
        FormDataVo formDataVo = new FormDataVo();
        formDataVo.setObjectId(objectId);

        Map<String,List<RecordVo>> forms = new HashMap<String,List<RecordVo>>();

        for (int i = 0; i < formVos.size(); i++) {
            FormConfigVo formVo = formVos.get(i);
            List<RecordVo> recordVos = objectId != null ? this.getOneFormData(formVo, objectId) : new ArrayList<>();
            forms.put(formVo.getTableName(),recordVos);
        }

        formDataVo.setForms(forms);

        return formDataVo;
    }



    /**
     * 获取单个form数据
     * @param formVo
     * @param objectId
     * @return
     */
    public List<RecordVo> getOneFormData(FormConfigVo formVo, Long objectId) {
        String selectDataSql = MysqlSqlUtil.generateSelectDataSql(formVo, objectId);
        List<Map<String, Object>> maps = jdbcTemplateBiz.queryForList(selectDataSql);

        List<RecordVo> recordVos = new ArrayList<RecordVo>();
        for (int i = 0; i < maps.size(); i++) {
            Map<String, Object> data = maps.get(i);
            RecordVo recordVo = new RecordVo();
            Long id = (Long)data.get(MysqlSqlUtil.idColumn);
            recordVo.setId(id);

            // 数据转换
            Map<String,RecordDetailVo> fieldsFront = this.getFieldsConvert(data) ;
            recordVo.setFields(fieldsFront);
            recordVos.add(recordVo);
        }
        return recordVos;
    }



    /**
     * Map<String, Object>转换为Map<String,RecordDetailVo>
     * @param data
     * @return
     */
    private Map<String,RecordDetailVo> getFieldsConvert(Map<String, Object> data){
        Map<String,RecordDetailVo> map = new HashMap<String, RecordDetailVo>() ;

        if (data!=null && data.size()>0) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                RecordDetailVo recordDetailVo = new RecordDetailVo() ;
                recordDetailVo.setValue((entry.getValue()!=null) ? entry.getValue().toString() : "");

                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                map.put(entry.getKey(), recordDetailVo) ;
            }
        }

        return map ;
    }



    /**
     * 保存动态表单数据(所有form中display=0的字段)
     * @param formsVo
     * @param studyId
     * @param studyGroupId
     * @return
     */
    @Transactional
    public boolean saveDisplayFormData(FormsVo formsVo, Long studyId, Long studyGroupId, Long centerId){
        boolean res = false;
        try {
            StudyObject studyObject = studyObjectManager.insertStudyObject(studyId, studyGroupId, centerId) ;

            res = this.saveData(formsVo, studyObject.getId()) ;

            // 判断每次点击新增研究对象时，如果研究对象一个字段也不填，手动生成一条数据
            if (studyObject.getId()!=null) {
                formBiz.createFormAddNewRow(formsVo,studyId, studyObject.getId()) ;
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }
        return res;
    }



    /**
     * 保存动态表单数据
     * @param formsVo
     * @param objectId
     * @return
     */
    private boolean saveData(FormsVo formsVo, Long objectId){
        boolean res = false;
        try {
            List<FormConfigVo> formConfigs = formsVo.getFormConfigs();
            // 取出前端传入的值
            Map<String,List<RecordVo>> newFormdata = formsVo.getData().getForms();

            // 匹配出更改的form
            Map<String, List<RecordVo>> changedForms = compareForms(newFormdata);

            this.saveDataRecu(changedForms, objectId);

            res = true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return res;
    }



    /**
     * 点击按钮 （0: 保存按钮  1：提交按钮  2：通过按钮  3：不通过按钮）
     * 修改某个研究对象的某个CRF 表单状态( 0: 已填完；1：未填完  2：未开始填  3:待审核  4:已审核  5：待修改 )
     *
     * @param formsVo
     * @param objectId
     * @param saveFlag
     * @return
     */
    public boolean saveFormAuditState(FormsVo formsVo, Long objectId, Integer saveFlag){
        boolean res = false;
        FormConfigVo formConfigVo = formsVo.getFormConfigs().get(0);
        String tableName = formConfigVo.getTableName();
        if (saveFlag == ResearchConsts.BUTTON_SUBMIT) {
            // 点击提交按钮, CRF 表单状态 变为 “3:待审核”
            formBiz.updateFormState(tableName,ResearchConsts.COLLECT_FLAG_AUDITING,objectId);

        } else if (saveFlag == ResearchConsts.BUTTON_PASS) {
            // 点击通过按钮, CRF 表单状态 变为 “4:已审核”
            formBiz.updateFormState(tableName,ResearchConsts.COLLECT_FLAG_AUDITED,objectId);
        }else if (saveFlag == ResearchConsts.BUTTON_NOT_PASS) {
            // 点击不通过按钮, CRF 表单状态 变为 “5：待修改”
            formBiz.updateFormState(tableName,ResearchConsts.COLLECT_FLAG_MODIDYING,objectId);

        }
        res=true;

        return res ;
    }




    /**
     * 保存单张动态表单的数据
     * @param formsVo
     * @param objectId
     * @param saveFlag 按钮编号
     * @return
     */
    @Transactional
    public Msg saveOneFormInfo(FormsVo formsVo, Long objectId, Integer saveFlag) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            // 查询CRF以及所有字段都已经创建列
            boolean checkCrfConfigValid = formConfigManager.checkCrfConfigValid(formsVo) ;
            if (checkCrfConfigValid) {
                studyObjectManager.updateStudyObjectById(objectId) ;

                // 保存 form 数据
                boolean res = this.saveData(formsVo, objectId) ;
                msg.setSucFlag(res);

                // 获取更新成功的数据
                FormModifiyVo formModifiyVo = formConfigManager.getFormModifiyInfo(formsVo, objectId) ;
                msg.setData(formModifiyVo);

                // 编辑crf 点击提交，通过，不通过状态更改
                this.saveFormAuditState(formsVo, objectId, saveFlag) ;

            }else {
                log.info("checkCrfConfigValid===[{}]", checkCrfConfigValid);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return msg ;
    }





    private Map<String,List<RecordVo>>  compareForms(Map<String,List<RecordVo>> formData){
        Map<String,List<RecordVo>> changedForm = new HashMap<>();
        Set<String> tableNames = formData.keySet();
        for (String tableName : tableNames) {
            List<RecordVo> recordVoList = this.filterFormByTableName(tableName, formData);
            List<RecordVo> changedRecords = compareFormRecord(recordVoList);
            if(changedRecords != null && changedRecords.size() >0){
                changedForm.put(tableName,changedRecords);
            }
        }
        return changedForm;
    }


    /**
     * 根据表名提取form
     * @param tableName
     * @param formData
     * @return
     */
    private List<RecordVo> filterFormByTableName(String tableName, Map<String,List<RecordVo>> formData){
        List<RecordVo> recordVos = null;
        Set<String> tableNames = formData.keySet();
        for (String tn : tableNames) {
            if(tableName.equals(tn)){
                recordVos = formData.get(tableName);
            }
        }
        return recordVos;
    }



    private List<RecordVo>  compareFormRecord(List<RecordVo> newRecordVos){
        for (RecordVo newRecordVo : newRecordVos) {
            if(newRecordVo.getId() != null){
                newRecordVo.setOperationType(OperationConstant.OperationType.UPDATE.getType());
            }else{
                // 新增的记录
                //排除字段为空的field
                newRecordVo.setOperationType(OperationConstant.OperationType.INSERT.getType());
            }
        }
        return newRecordVos;
    }




    /**
     * 递归生成SQL
     * @param formDatas
     * @param objectId
     */
    private void saveDataRecu(Map<String, List<RecordVo>> formDatas, Long objectId){
        for (String key : formDatas.keySet()) {
            List<RecordVo> recordVos = formDatas.get(key);
            String tableName = key;
            this.saveDataRecu(tableName, recordVos, objectId);
        }
    }



    /**
     * 递归操作动态表单数据
     * @param tableName
     * @param recordVos
     * @param objectId
     */
    private void saveDataRecu(String tableName, List<RecordVo> recordVos, Long objectId){
        for (int i = 0; i < recordVos.size(); i++) {
            Long id = null;
            RecordVo recordVo = recordVos.get(i);
            // 新增记录
            if(recordVo.getOperationType() == OperationConstant.OperationType.INSERT.getType()){
                id = this.insertData(recordVo, tableName, objectId);
                recordVo.setId(id);
                // 编辑记录
            }else if(recordVo.getOperationType() == OperationConstant.OperationType.UPDATE.getType()){
                id = this.updateData(recordVo, tableName, objectId);
                // 删除记录
            }else if(recordVo.getOperationType() == OperationConstant.OperationType.DELETE.getType()){
                this.delData(tableName,recordVo.getId());
            }
        }
    }



    /**
     * 把前端拿到的Map<String,RecordDetailVo>转换为Map<String,Object>
     * @param recordVo
     * @return
     */
    public Map<String,Object> getFieldsIntoDB(RecordVo recordVo) {
        Map<String,Object> map = new HashMap<String,Object>() ;

        Map<String,RecordDetailVo> fieldsFront = recordVo.getFields() ;
        if (fieldsFront!=null && fieldsFront.size()>0) {
            for (Map.Entry<String, RecordDetailVo> entry : fieldsFront.entrySet()) {
                RecordDetailVo recordDetailVo = entry.getValue() ;
                String value  = recordDetailVo!=null ? recordDetailVo.getValue(): "" ;
                if (value==null) {
                    value = "" ;
                }
                map.put(entry.getKey(), value) ;
            }
        }

        return map ;
    }





    // 新增数据
    public Long  insertData(RecordVo recordVo,String tableName,Long objectId){
        String saveSql = generateSaveSql(tableName, recordVo, objectId);
        Long id = recordVo.getId();
        if(StringUtils.isNotEmpty(saveSql)){
            id = jdbcTemplateBiz.insert(saveSql, this.getFieldsIntoDB(recordVo));
        }
        return id;
    }




    // 编辑数据
    public Long  updateData(RecordVo recordVo,String tableName,Long objectId){
        String saveSql = generateSaveSql(tableName,recordVo,objectId);
        Long id = recordVo.getId();
        if(StringUtils.isNotEmpty(saveSql)){
            jdbcTemplateBiz.update(saveSql, this.getFieldsIntoDB(recordVo));
        }
        return id;
    }



    // 刪除数据
    public void  delData(String tableName,Long id){
        String saveSql = MysqlSqlUtil.generateDelRecordSql(tableName,id);
        jdbcTemplateBiz.updateDel(saveSql);
    }



    /**
     * 生成更新或者插入sql
     * @param tableName
     * @param recordVo
     * @param objectId
     * @return
     */
    private String generateSaveSql(String tableName,RecordVo recordVo,Long objectId){
        if(recordVo.getId() != null){
            return MysqlSqlUtil.generateUpdateDataSql(tableName, this.getFieldsIntoDB(recordVo), recordVo.getId());
        }else{
            return MysqlSqlUtil.generateInsertDataSql(tableName, this.getFieldsIntoDB(recordVo), objectId);
        }
    }



    /**
     * 更新动态表单数据为 已删除
     * @param objectId
     * @return
     */
    public  boolean delFormData(Long objectId) {
        boolean res = true;
        try {
            List<FormConfigVo> formConfigs = formConfigManager.getFormConfigByConfigId(objectId, null);
            // 删除Form
            for (int i = 0; i < formConfigs.size(); i++) {
                FormConfigVo formConfig = formConfigs.get(i);
                this.delOneFormData(formConfig, objectId);
            }
        }catch (Exception e){
            e.printStackTrace();
            res = false;
        }
        return res;
    }


    /**
     * 删除某一个form data
     * @param formConfig
     * @param objectId
     */
    private  void  delOneFormData(FormConfigVo formConfig, Long objectId) {
        try {
            List<CrfFieldVo> fields = formConfig.getFieldVoList() ;

            // 查询数据
            List<RecordVo> formData = this.getOneFormData(formConfig, objectId);

            for (RecordVo record : formData) {
                //操作标记为删除
                this.setOperationTypeRecu(record, OperationConstant.OperationType.DELETE.getType());

                //递归执行删除操作
                this.saveDataRecu(formConfig.getTableName(), formData, objectId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 设置数据标识：已删除
     * @param recordVo
     * @param operationType
     * @return
     */
    private  RecordVo setOperationTypeRecu(RecordVo recordVo,int operationType){
        recordVo.setOperationType(operationType);
        return recordVo;
    }

    /**
     * 获取crf的状态
     * @param tableName
     * @param objectId
     * @return
     */
    public StatusVo selectStatus(String tableName,Long objectId) {
        return formBiz.selectStatus(tableName,objectId);
    }



}
