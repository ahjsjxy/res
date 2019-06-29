package com.ronglian.kangrui.saas.research.common.msg;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-06-14 22:40
 */
public class TableResultResponse<T> extends BaseResponse {

    private Long totalCount;

    List<T> data;


    public TableResultResponse(long total, List<T> rows) {
        this.totalCount = total ;
        this.data = rows ;
    }


    TableResultResponse<T> total(int total) {
        this.setTotalCount(new Long(total));
        return this;
    }

    TableResultResponse<T> total(List<T> rows) {
        this.setData(rows);
        return this;
    }


    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

}
