package com.ronglian.kangrui.saas.research.sci.util;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author lanyan
 * @create 2019-04-25 10:23
 **/
public class ListDifferentUtil {

    /**
     * 获取删除的list
     * @param beforeList
     * @param afterList
     * @return
     */
    public static List<Long> getDeleteList(List<Long> beforeList, List<Long> afterList) {
        List<Long> tempBeforeList = new ArrayList<Long>() ;
        List<Long> tempAfterList = new ArrayList<Long>() ;
        if(beforeList!=null && beforeList.size()>0) {
            beforeList.forEach(beforeId->{
                if (beforeId!=null) {
                    tempBeforeList.add(beforeId) ;
                }
            });
        }

        if (afterList!=null && afterList.size()>0) {
            afterList.forEach(afterId->{
                if (afterId!=null) {
                    tempAfterList.add(afterId) ;
                }
            });
        }

        //delete
        if (tempBeforeList!=null && tempBeforeList.size()>0) {
            tempBeforeList.removeAll(tempAfterList) ;
        }
        return tempBeforeList ;
    }


    /**
     * 获取新增的list
     * @param beforeList
     * @param afterList
     * @return
     */
    public static List<Long> getInsertList(List<Long> beforeList, List<Long> afterList) {
        List<Long> tempBeforeList = new ArrayList<Long>() ;
        List<Long> tempAfterList = new ArrayList<Long>() ;
        if(beforeList!=null && beforeList.size()>0) {
            beforeList.forEach(beforeId->{
                if (beforeId!=null) {
                    tempBeforeList.add(beforeId) ;
                }
            });
        }

        if (afterList!=null && afterList.size()>0) {
            afterList.forEach(afterId->{
                if (afterId!=null) {
                    tempAfterList.add(afterId) ;
                }
            });
        }

        // insert
        if (tempAfterList!=null && tempAfterList.size()>0) {
            tempAfterList.removeAll(tempBeforeList) ;
        }

        return tempAfterList ;
    }

}
