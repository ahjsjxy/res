package com.ronglian.kangrui.saas.research.sci.manager;

import com.google.common.collect.Maps;
import com.ronglian.kangrui.saas.research.admin.api.IHospCenterService;
import com.ronglian.kangrui.saas.research.admin.vo.HospCenterVo;
import com.ronglian.kangrui.saas.research.common.constant.RestCodeConstants;
import com.ronglian.kangrui.saas.research.common.msg.ObjectRestResponse;
import com.ronglian.kangrui.saas.research.common.vo.Msg;
import com.ronglian.kangrui.saas.research.sci.biz.*;
import com.ronglian.kangrui.saas.research.sci.consts.ButtonConstant;
import com.ronglian.kangrui.saas.research.sci.consts.ResearchConsts;
import com.ronglian.kangrui.saas.research.sci.entity.Crf;
import com.ronglian.kangrui.saas.research.sci.entity.StudyGroup;
import com.ronglian.kangrui.saas.research.sci.entity.StudyObject;
import com.ronglian.kangrui.saas.research.sci.entity.StudyUserGroupCenter;
import com.ronglian.kangrui.saas.research.sci.mapper.StudyMapper;
import com.ronglian.kangrui.saas.research.sci.vo.*;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-07 17:21
 **/
@Service
@Slf4j
public class StudyObjectManager {

    @Autowired
    private StudyObjectBiz studyObjectBiz ;

    @Autowired
    private CrfBiz crfBiz ;

    @Autowired
    private CrfDictFieldBiz crfDictFieldBiz ;

    @Autowired
    private FormConfigManager formConfigManager ;

    @Autowired
    private FormDataManager formDataManager ;

    @Autowired
    private CrfDictFieldManager crfDictFieldManager ;

    @Autowired
    private CrfFormConfigManager crfFormConfigManager ;

    @Autowired
    private StudyMapper  studyMapper ;

    @Autowired
    private FormBiz formBiz;

    @Autowired
    private IHospCenterService iHospCenterService;

    @Autowired
    private StudyUserGroupCenterBiz studyUserGroupCenterBiz;



    /**
     * 根据id 查询研究对象
     * @param objectId
     * @return
     */
    public StudyObject selectById(Long objectId) {
        return studyObjectBiz.selectById(objectId) ;
    }



    /**
     * 插入一个研究对象
     * @param studyId
     * @param studyGroupId
     * @return
     */
    public StudyObject insertStudyObject(Long studyId, Long studyGroupId, Long centerId) {
        return studyObjectBiz.insertStudyObject(studyId, studyGroupId, centerId) ;
    }



    /**
     * 根据 study object id 更新下 更新时间
     * @param objectId
     * @return
     */
    public StudyObject updateStudyObjectById(Long objectId) {
        return  studyObjectBiz.updateStudyObjectById(objectId) ;
    }

    public StudyObject selectStudyObject(Long objectId) {
        return  studyObjectBiz.selectStudyObject(objectId) ;
    }




    /**
     *  查询研究对象列表
     * @param studyId
     * @param studyGroupId
     * @return
     */
    public List<StudyObjectVo> queryStudyObjectList(Long studyId, Long centerId,Long studyGroupId,List<Long> centerIds) {
        List<StudyObjectVo> studyObjectList = studyObjectBiz.queryStudyObjectList(studyId, centerId,studyGroupId,centerIds) ;
        if (studyObjectList!=null && studyObjectList.size()>0) {
            for(StudyObjectVo studyObjectVo : studyObjectList) {

                //某行转换为key,value
                Map<String, Object> map = this.getOneRowMap(studyId, studyObjectVo) ;
                studyObjectVo.setFieldMap(map);

                studyObjectVo.setId(null);
                studyObjectVo.setCreateTime(null);
                studyObjectVo.setUpdateTime(null);
                studyObjectVo.setCenterName(null);
            }
        }

        return studyObjectList ;
    }


    /**
     * 根据登陆用户ID和项目ID，获取中心权限列表（若有队列ID，则继续过滤队列下的中心列表）
     * @param studyId
     * @return
     */
    public List<Long> selectCenterByStudyAndUser(Long studyId){
        Long userId = ShiroService.getCurrentUser().getId() ;
        List<StudyUserGroupCenter> centerList = studyUserGroupCenterBiz.selectCenterByStudyAndUser(studyId, userId, null);
        List<Long> centerIds = centerList.stream().map(item->item.getCenterId()).collect(Collectors.toList());
        return centerIds;
    }





    /**
     * 转换map
     * @param studyId
     * @param studyObjectVo
     * @return
     */
    private Map<String, Object> getOneRowMap(Long studyId, StudyObjectVo studyObjectVo) {
        Map<String, Object> map = new HashMap<>() ;
        map.put("id", studyObjectVo.getId()) ;
        map.put("createTime", studyObjectVo.getCreateTime()) ;
        map.put("updateTime", studyObjectVo.getUpdateTime()) ;
        map.put("centerName",studyObjectVo.getCenterName());

        Map<String, Object> fieldMap = this.getFieldList(studyId, studyObjectVo.getId()) ;
        map.putAll(fieldMap);

        Map<String, StudyObjectFormVo> formMap = this.getCrfFormList(studyId, studyObjectVo.getId()) ;
        map.putAll(formMap);

        return map ;
    }




    /**
     * 根据object id判断当前的form 表单是否填写完整
     * @return
     */
    private Map<String, StudyObjectFormVo> getCrfFormList(Long studyId, Long objectId) {
        Map<String, StudyObjectFormVo> map = new HashMap<String, StudyObjectFormVo>() ;

        List<FormConfigVo> formConfigVoList = crfFormConfigManager.getNormalFormConfigVoList(studyId) ;
        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                StudyObjectFormVo temp = new StudyObjectFormVo() ;
                temp.setValue(formConfigVo.getId());

                // 获取CRF表单状态，数据库状态（2:填写中  3:待审核  4:已审核  5：待修改）
                // 若是填写中：需把状态改为具体的 （ 0: 已填完；1：未填完  2：未开始填），其他保持原来状态
                Integer status = this.getCrfStatus(formConfigVo, objectId, studyId) ;
                temp.setStatus(status);

                // crf 是否有新的列未生成字段到DB
                boolean hasNewFields = crfDictFieldManager.checkHasFieldsGenerateToDb(formConfigVo.getId()) ;
                temp.setHasNewFields(hasNewFields); //【true：有新列  false：无】

                // 如果crf 下面没有字段，则设置editFlag
//                boolean editFlag = (formConfigVo.getFieldVoList()!=null
//                        && formConfigVo.getFieldVoList().size()>0) ;
                temp.setEditFlag(Boolean.TRUE);

                // map.put(formConfigVo.getTableName(), temp) ;
                map.put("formflag_"+formConfigVo.getId(), temp) ;

            }
        }

        return map ;
    }




    /**
     * 获取采集是否完成逻辑(0: 已填完；1：未填完  2：未开始填)
     * @param formConfigVo
     * @param objectId
     * @return
     */
    public Integer getCollectFlagInfo(FormConfigVo formConfigVo, Long objectId, Long studyId) {
        Integer collectFlag = ResearchConsts.COLLECT_FLAG_NOT_BEGIN; // 采集是否完成  (2：未开始填)

        CrfDictFieldVo crfDictFieldVo = new CrfDictFieldVo() ;
        crfDictFieldVo.setCrfFormId(formConfigVo.getId());
        crfDictFieldVo.setStudyId(studyId);
        List<CrfFieldVo> crfFieldVoList = crfDictFieldBiz.getCrfAndFieldGenerateToDbList(crfDictFieldVo) ;
        if (crfFieldVoList!=null && crfFieldVoList.size()>0) {
            //判断填写中的状态  0: 已填完；1：未填完  2：未开始填
            List<RecordVo> recordVoList = formDataManager.getOneFormData(formConfigVo, objectId);
            if (recordVoList!=null && recordVoList.size()>0) {
                RecordVo recordVo = (RecordVo) recordVoList.get(0);
                Map<String,Object> fieldsMap = formDataManager.getFieldsIntoDB(recordVo) ;

                // 判断 fieldsMap 是否含有列名 id
                if (fieldsMap!=null && fieldsMap.size()>0) {
                    if (fieldsMap.containsKey(ResearchConsts.ID_COLUMN)) {
                        fieldsMap.remove(ResearchConsts.ID_COLUMN) ;
                    }

                    collectFlag = (fieldsMap.containsValue(null) || fieldsMap.containsValue("") ? ResearchConsts.COLLECT_FLAG_NOT_FINISHED :
                            ResearchConsts.COLLECT_FLAG_FINISHED ) ;


                    // 判断如果指标不是“已填完”，校验若满足只有一个指标值不为空，则为“未填完”，否则“未开始填”
                    if(collectFlag!=ResearchConsts.COLLECT_FLAG_FINISHED){
                        Collection<String> fields = (Collection)fieldsMap.values();
                        int allEmptyFlag = ResearchConsts.ALL_EMPTY_FLAG_YES ;
                        for(String item : fields){
                            if(StringUtils.isNotBlank(item)){
                                allEmptyFlag = ResearchConsts.ALL_EMPTY_FLAG_NO ;
                                break;
                            }
                        }

                        if(allEmptyFlag == ResearchConsts.ALL_EMPTY_FLAG_YES){
                            collectFlag = ResearchConsts.COLLECT_FLAG_NOT_BEGIN;
                        }
                    }

                }

            }
        }

        return collectFlag ;
    }



    /**
     * 获取研究对象对应的crf的状态字段的值
     * @param formConfigVo
     * @param objectId
     * @return
     */
    private Integer getCrfStatus(FormConfigVo formConfigVo, Long objectId, Long studyId) {
        String tableName = formConfigVo.getTableName();
        Integer status = ResearchConsts.COLLECT_FLAG_NOT_BEGIN ;// 2：未开始填

        CrfDictFieldVo temp = new CrfDictFieldVo() ;
        temp.setCrfFormId(formConfigVo.getId());
        temp.setStudyId(studyId);
        List<CrfFieldVo> list = crfDictFieldBiz.getCrfAndFieldGenerateToDbList(temp);
        if (list!=null && list.size() > 0) {
            StatusVo statusVo = formBiz.selectStatus(tableName,objectId);
            status = (statusVo!=null) ?  statusVo.getStatus() : status ;

            // 状态：若是填写中：需把状态改为具体的 （ 0: 已填完；1：未填完  2：未开始填），其他保持原来状态
            if (status == ResearchConsts.COLLECT_FLAG_WRITING) {
                status = this.getCollectFlagInfo(formConfigVo, objectId, studyId) ;
            }
        }

        return status;
    }





    /***
     * 获取 object id 每一列的值拼成集合
     * @param studyId
     * @param objectId
     * @return
     */
    private Map<String, Object> getFieldList(Long studyId, Long objectId) {
        Map<String, Object> map = new HashMap<>() ;

        List<FormConfigVo> formConfigVoList = formConfigManager.getDisplayFormConfigVoList(studyId) ;

        if (formConfigVoList!=null && formConfigVoList.size()>0) {
            for (FormConfigVo formConfigVo : formConfigVoList) {
                List<RecordVo> recordVoList = formDataManager.getOneFormData(formConfigVo, objectId);
                if (recordVoList!=null && recordVoList.size()>0) {
                    RecordVo recordVo = (RecordVo) recordVoList.get(0) ;
                    Map<String,Object> fieldsMap = formDataManager.getFieldsIntoDB(recordVo) ;
                    if (formConfigVo.getFieldVoList()!=null && formConfigVo.getFieldVoList().size()>0) {
                        for (CrfFieldVo crfFieldVo : formConfigVo.getFieldVoList()) {
                            if (fieldsMap.get(crfFieldVo.getFieldName())!=null) {
                                String value = fieldsMap.get(crfFieldVo.getFieldName()).toString() ;
                                map.put(crfFieldVo.getFieldName(), value) ;

                            }
                        }
                    }
                } else {
                    // do sth
                    if (formConfigVo.getFieldVoList()!=null && formConfigVo.getFieldVoList().size()>0) {
                        for (CrfFieldVo crfFieldVo : formConfigVo.getFieldVoList()) {
                            map.put(crfFieldVo.getFieldName(), "") ;
                        }
                    }
                }
            }
        }

        return map ;
    }


    /**
     * 中心列表
     * @param studyId
     * @return
     */
    public ObjectRestResponse getCenterList(Long studyId) {
        //获取所有的中心
        List<HospCenterVo> centerList = iHospCenterService.getHospCenterList(null);

        //获取勾选的中心权限
        Long userId = ShiroService.getCurrentUser().getId();
        List<StudyUserGroupCenter> studyUserGroupCenterList = studyUserGroupCenterBiz.selectCenterByStudyAndUser(studyId, userId, null);
        List<Long> centerIds = studyUserGroupCenterList.stream().map(item->item.getCenterId()).collect(Collectors.toList());

        centerList = centerList.stream().filter(each->centerIds.contains(each.getId())).collect(Collectors.toList());
        HospCenterVo center = iHospCenterService.selectDefaultCenter(userId);
        Long centerId = center!=null?center.getId():-1L;
        //下拉列表没有默认中心，则默认-1
        if(center!=null && !centerIds.contains(center.getId())){
            centerId=-1L;
        }

        Map<String,Object> map = Maps.newHashMap();
        map.put("centerList",centerList);
        map.put("centerDefault",centerId);
        return new ObjectRestResponse().data(map);
    }


    /**
     * 研究对象列头
     * @param studyId
     * @return
     */
    public List<HeaderVo> getHeaderList(Long studyId) {
        List<HeaderVo> headerList = new ArrayList<HeaderVo>() ;

        HeaderVo headerVo = new HeaderVo() ;
        headerVo.setName("createTime");
        headerVo.setTitle("录入时间");
        headerList.add(headerVo);

        headerVo = new HeaderVo();
        headerVo.setName("centerName");
        headerVo.setTitle("中心名称");
        headerList.add(headerVo);


        // 研究对象的展示列头
        List<FormConfigVo> formConfigVoList = formConfigManager.getDisplayFormConfigVoList(studyId) ;
        if (formConfigVoList!=null  && formConfigVoList.size()>0) {
            List<FormConfigVo> formValidList = formConfigVoList.stream().filter(formConfigVo -> (formConfigVo.getFieldVoList()!=null && formConfigVo.getFieldVoList().size()>0)).distinct().collect(Collectors.toList());
            for (FormConfigVo temp : formValidList) {
                List<CrfFieldVo> fieldVoList = temp.getFieldVoList() ;
                if (fieldVoList!=null && fieldVoList.size()>0) {
                    for (CrfFieldVo crfFieldVo : fieldVoList) {
                        headerVo = new HeaderVo() ;
                        headerVo.setName(crfFieldVo.getFieldName());
                        headerVo.setTitle(crfFieldVo.getLabel());
                        headerList.add(headerVo);

                    }
                }

            }
        }


        headerVo = new HeaderVo() ;
        headerVo.setName("updateTime");
        headerVo.setTitle("更新时间");
        headerList.add(headerVo);


        // 研究对象的CRF
        List<CrfParentVo> crfParentVoList = formConfigManager.getCrfFormListByStudyId(studyId) ;
        if (crfParentVoList!=null && crfParentVoList.size()>0) {
            for (CrfParentVo crfParentVo : crfParentVoList) {
                Crf crf = crfBiz.selectById(crfParentVo.getId()) ;

                headerVo = new HeaderVo() ;

                // headerVo.setName(crf.getTableName());
                headerVo.setName("formflag_"+crf.getId());
                headerVo.setTitle(crfParentVo.getName());
                headerList.add(headerVo);

            }
        }

        return headerList ;
    }



    /**
     *  查询某课题下的 队列-列表
     * @param studyId
     * @return
     */
    public List<StudyGroup> getStudyGroupList(Long studyId, Long centerId, List<Long> centerIds) {
        Long userId = ShiroService.getCurrentUser().getId() ;
        return studyObjectBiz.getStudyGroupList(studyId, centerId, userId, centerIds) ;
    }




    /**
     * 根据主键ID更新  研究对象删除
     * @param objectId
     */
    public void delStudyObject(Long objectId) {
        studyObjectBiz.delStudyObject(objectId);
    }




    /**
     * 删除研究对象以及相关的form
     * @param objectIdStr
     * @return
     */
    public Msg delStudyObjectForm(String objectIdStr) {
        Msg msg = new Msg() ;
        msg.setSucFlag(Boolean.FALSE);

        try {
            List<Long> objectList = Arrays.asList(objectIdStr.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            if (objectList!=null && objectList.size()>0) {
                int num = 0 ;
                for (Long objectId : objectList) {
                    // 更新 研究对象主表的状态为已删除
                    this.delStudyObject(objectId);

                    // 更新 相关的动态表单数据为已删除
                    formDataManager.delFormData(objectId);
                    num++ ;
                }

                if (num>0) {
                    msg.setSucFlag(Boolean.TRUE);
                    msg.setDesc(ResearchConsts.STUDY_OBJECT_DEL_SUC_MSG);//删除研究对象成功
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
     *  校验 研究对象列表-(添加/删除/设置)权限
     * @param studyId
     * @return
     */
    public StudyObjectButtonVo studyObjectButtonAuthority(Long studyId){
        Long userId = ShiroService.getCurrentUser().getId();
        StudyVo studyVo = new StudyVo();
        studyVo.setId(studyId);
        studyVo.setUserId(userId);
        List<ButtonCodeVo> allButtons = studyMapper.getButtonListByStudyAndUserId(studyVo);
        List<String> codeList = allButtons.stream().map(item->item.getCode()).collect(Collectors.toList());
        StudyObjectButtonVo studyObjectButtonVo = this.buttonAuthority(codeList);
        return studyObjectButtonVo;
    }


    /**
     * 判断 研究对象列表-(添加/删除/设置)权限
     * @param codeList
     * @return
     */
    private StudyObjectButtonVo buttonAuthority(List<String> codeList){
        StudyObjectButtonVo sob = new StudyObjectButtonVo(ButtonConstant.BUTTON_DISABLE, ButtonConstant.BUTTON_DISABLE, ButtonConstant.BUTTON_DISABLE);
        for(String each : codeList){
            switch (each){
                case ButtonConstant.STUDY_OBJECT_ADD:
                    sob.setAddEnableFlag(ButtonConstant.BUTTON_ENABLE);
                    break;
                case ButtonConstant.STUDY_OBJECT_DELETE:
                    sob.setDeleteEnableFlag(ButtonConstant.BUTTON_ENABLE);
                    break;
                case ButtonConstant.STUDY_OBJECT_SETTING:
                    sob.setSettingEnableFlag(ButtonConstant.BUTTON_ENABLE);
                    break;
            }
        }
        return sob;
    }


}
