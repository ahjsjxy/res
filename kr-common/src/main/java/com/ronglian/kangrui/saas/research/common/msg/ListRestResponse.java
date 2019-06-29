package com.ronglian.kangrui.saas.research.common.msg;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author kr
 * @create 2017-06-09 7:32
 */
public class ListRestResponse<T> {
    String  msg;
    List<T> result;
    int     count;



    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ListRestResponse count(int count) {
        this.setCount(count);
        return this;
    }

    public ListRestResponse count(Long count) {
        this.setCount(count.intValue());
        return this;
    }

    public ListRestResponse msg(String msg) {
        this.setMsg(msg);
        return this;
    }

    public ListRestResponse result(List<T> result) {
        this.setResult(result);
        this.setCount(result!= null ? result.size(): 0 );
        return this;
    }

}
