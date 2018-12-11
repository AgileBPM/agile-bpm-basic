package com.dstz.base.api.request;

import com.dstz.base.api.query.FieldSort;

import java.util.ArrayList;
import java.util.List;


/**
 * 分页请求参数
 *
 * @author Administrator
 */
public class RequestPage extends RequestParam {

    //当前页
    private int pageNo = 1;
    private int pageSize = 20;
    private List<FieldSort> orders = new ArrayList<FieldSort>();

    public RequestPage() {

    }

    public RequestPage(int pageNo, int pageSize) {
        super();
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public List<FieldSort> getOrders() {
        return orders;
    }

    public void setOrders(List<FieldSort> orders) {
        this.orders = orders;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}

