package com.ronglian.kangrui.saas.research.common.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author admin
 * @description 返回基础类
 * @date 2017/11/29
 */
public class Msg implements Serializable{

    @Getter
    @Setter
    private Boolean sucFlag;

    @Getter
    @Setter
    private String code;

    @Getter
    @Setter
    private String desc;

    @Getter
    @Setter
    private Object data;

    public Msg(boolean sucFlag) {
        this.sucFlag = sucFlag;
    }

    public Msg() {
    }
}
