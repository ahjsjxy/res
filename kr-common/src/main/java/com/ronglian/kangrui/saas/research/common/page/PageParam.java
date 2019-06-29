package com.ronglian.kangrui.saas.research.common.page;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class PageParam implements Serializable{
    
    /**
    * 
    */
    @Getter
    @Setter
    private  Integer currentPage = 1;

    /**
     *
     */
    @Getter
    @Setter
    private Integer pageSize = 10;

}
