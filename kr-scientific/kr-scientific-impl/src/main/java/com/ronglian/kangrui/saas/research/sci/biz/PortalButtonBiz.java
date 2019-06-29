package com.ronglian.kangrui.saas.research.sci.biz;

import com.ronglian.kangrui.saas.research.common.biz.BaseBiz;
import com.ronglian.kangrui.saas.research.sci.entity.PortalButton;
import com.ronglian.kangrui.saas.research.sci.mapper.PortalButtonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-05-30 14:53
 **/
@Service
public class PortalButtonBiz extends BaseBiz<PortalButtonMapper, PortalButton> {

    @Autowired
    private PortalButtonMapper portalButtonMapper ;

}
