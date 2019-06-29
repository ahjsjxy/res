package com.ronglian.kangrui.saas.research.sci.consts;

import lombok.Getter;
import lombok.Setter;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-03-06 17:53
 **/
public class OperationConstant {

    public enum OperationType{

        INSERT(1),UPDATE(2),DELETE(3);

        OperationType(int type){
            this.type = type;
        }
        @Setter
        @Getter
        private int type;

    }
}
