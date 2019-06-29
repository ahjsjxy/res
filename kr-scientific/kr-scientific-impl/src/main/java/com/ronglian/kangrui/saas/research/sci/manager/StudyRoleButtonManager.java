package com.ronglian.kangrui.saas.research.sci.manager;

import com.ronglian.kangrui.saas.research.sci.biz.PortalButtonBiz;
import com.ronglian.kangrui.saas.research.sci.biz.StudyRoleButtonBiz;
import com.ronglian.kangrui.saas.research.sci.entity.PortalButton;
import com.ronglian.kangrui.saas.research.sci.entity.StudyRoleButton;
import com.ronglian.kangrui.saas.research.sci.vo.PortalButtonVo;
import com.ronglian.kangrui.saas.research.shirobase.service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 11:55
 **/
@Service
public class StudyRoleButtonManager {

    @Autowired
    private StudyRoleButtonBiz studyRoleButtonBiz ;

    @Autowired
    private PortalButtonBiz portalButtonBiz ;



    /**
     * 根据角色 列出所有的菜单，已选中的为checked
     * @param roleId
     * @return
     */
    public List<PortalButtonVo> getButtonByRoleId(Long studyId, Long roleId) {
        PortalButtonVo portalButtonVo = new PortalButtonVo() ;
        portalButtonVo.setStudyId(studyId);
        portalButtonVo.setRoleId(roleId);
        return studyRoleButtonBiz.getButtonByRoleId(portalButtonVo) ;
    }



    /**
     * 角色-按钮保存(项目角色-菜单开启和关闭)
     *
     * @param roleId
     * @param buttonId
     * @param checked
     *
     * @return
     */
    public boolean saveStudyRoleButtonInfo(Long roleId, Long buttonId, boolean checked) {
        boolean flag = Boolean.FALSE ;

        if (roleId!=null && buttonId!=null) {
            if (checked) {
                //选中-菜单开启
                List<PortalButtonVo> portalButtonVoList = this.getStudyRoleButtonExists(roleId, buttonId) ;
                if (!(portalButtonVoList!=null && portalButtonVoList.size()>0)) {
                    // 插入 study_role_button
                    StudyRoleButton studyRoleButton = this.initStudyRoleButton(roleId, buttonId) ;
                    studyRoleButtonBiz.insertSelective(studyRoleButton) ;

                    flag = Boolean.TRUE ;
                }
            }else {
                // 未选中-菜单关闭

                List<PortalButtonVo> portalButtonVoList = this.getStudyRoleButtonExists(roleId, buttonId) ;
                if (portalButtonVoList!=null && portalButtonVoList.size()>0) {
                    // 删除 study_role_button
                    StudyRoleButton studyRoleButton = this.initStudyRoleButton(roleId, buttonId) ;
                    studyRoleButtonBiz.delete(studyRoleButton);

                    flag = Boolean.TRUE ;
                }
            }
        }

        return flag ;
    }


    /**
     * 根据角色  查看角色对应的按钮  是否已存在数据
     * @param roleId
     * @param buttonId
     * @return
     */
    private List<PortalButtonVo> getStudyRoleButtonExists(Long roleId, Long buttonId) {
        PortalButtonVo temp = new PortalButtonVo() ;
        temp.setRoleId(roleId);
        temp.setId(buttonId);
        return studyRoleButtonBiz.getStudyRoleButtonExists(temp) ;
    }




    /**
     *  初始化 StudyRoleButton
     * @param roleId
     * @param buttonId
     * @return
     */
    private StudyRoleButton initStudyRoleButton(Long roleId, Long buttonId) {
        // 根据button id 查询button 详情
        PortalButton portalButton = portalButtonBiz.selectById(buttonId) ;

        // 删除 study_role_button
        StudyRoleButton studyRoleButton = new StudyRoleButton() ;
        studyRoleButton.setRoleId(roleId);
        studyRoleButton.setButtonCode(portalButton.getCode());

        return studyRoleButton ;
    }


    /**
     * 根据用户，查询用户在某个项目下的权限按钮集合
     * @param studyId
     * @param userId
     * @return
     */
    public List<StudyRoleButton> queryButtonByStudyIdAndUserId(Long studyId,Long userId){
        return studyRoleButtonBiz.queryButtonByStudyIdAndUserId(studyId,userId);
    }

    public Boolean checkHasAuthority(String buttonCode,Long studyId){
        Boolean rel = Boolean.FALSE;
        Long userId = ShiroService.getCurrentUser().getId() ;
        List<StudyRoleButton> studyRoleButtonList = queryButtonByStudyIdAndUserId(studyId,userId);
        List<String> buttonList = studyRoleButtonList.stream().map(item->item.getButtonCode()).collect(Collectors.toList());
        if(buttonList.contains(buttonCode)){
            rel=Boolean.TRUE;
        }
        return rel;
    }

}
