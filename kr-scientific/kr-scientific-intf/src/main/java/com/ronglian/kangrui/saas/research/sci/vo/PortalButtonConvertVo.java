package com.ronglian.kangrui.saas.research.sci.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 14:29
 **/
@Data
public class PortalButtonConvertVo implements Serializable {
    private Long roleId ;//角色ID
    private Long buttonId ;//选中菜单ID
    private boolean checked ;//是否选中 (true：选中，false : 未选中)
}
