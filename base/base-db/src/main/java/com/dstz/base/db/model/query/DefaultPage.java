package com.dstz.base.db.model.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.dstz.base.api.Page;
import com.dstz.base.api.query.FieldSort;
import com.dstz.base.core.util.ToStringUtil;

/**
 * 分页查询对象
 */
public class DefaultPage extends RowBounds implements Page, Serializable {
	private static final long serialVersionUID = 1500879478877475515L;
	public final static int NO_PAGE = 1;
    /**
     * 页号
     */
    protected int pageNo = NO_PAGE;
    /**
     * 分页大小
     */
    protected int pageSize = DEFAULT_PAGE_SIZE;
    /**
     * 分页排序信息
     */
    protected List<FieldSort> orders = new ArrayList<>();
    
    /**
     * 是否显示总条数
     */
    private boolean isShowTotal = true;

    public DefaultPage() {
    }

    public DefaultPage(RowBounds rowBounds) {
        this.pageNo = (rowBounds.getOffset() / rowBounds.getLimit()) + 1;
        this.pageSize = rowBounds.getLimit();
    }

    
    /**
     * 
     * @param pageNo 页码
     * @param pageSize 分页大小
     */
    public DefaultPage(int pageNo, int pageSize) {
        this(pageNo, pageSize, new ArrayList<FieldSort>(), true);
    }

    /**
     * Just sorting, default containsTotalCount = false
     * @param orders
     */
    public DefaultPage(List<FieldSort> orders) {
        this(NO_PAGE, NO_ROW_LIMIT, orders, false);
    }

    /**
     * Just sorting, default containsTotalCount = false
     * @param order
     */
    public DefaultPage(FieldSort... order) {
        this(NO_PAGE, NO_ROW_LIMIT, order);
        this.isShowTotal = false;
    }

    public DefaultPage(int page, int limit, FieldSort... order) {
        this(page, limit, Arrays.asList(order), true);
    }

    public DefaultPage(int page, int limit, List<FieldSort> orders) {
        this(page, limit, orders, true);
    }

    public DefaultPage(int pageNo, int pageSize, List<FieldSort> orders, boolean isShowTotal) {
        this.pageNo = pageSize;
        this.pageSize = pageSize;
        this.orders = orders;
        this.isShowTotal = isShowTotal;
    }


    public int getPage() {
        return pageNo;
    }

    public void setPage(int page) {
        this.pageNo = page;
    }

    public int getLimit() {
        return pageSize;
    }

    public void setLimit(int limit) {
        this.pageSize = limit;
    }

    public List<FieldSort> getOrders() {
        List<FieldSort> list = orders;
        return list;
    }

    public void setOrders(List<FieldSort> orders) {
        this.orders = orders;
    }

    @Override
    public int getOffset() {
        if (pageNo >= 1) {
            return (pageNo - 1) * pageSize;
        }
        return 0;
    }

    @Override
    public String toString() {
       return ToStringUtil.toString(this);
    }


    @Override
    public Integer getPageNo() {
        return this.getPage();
    }

    @Override
    public Integer getPageSize() {
        return this.getLimit();
    }

    @Override
    public Integer getStartIndex() {
        return this.getOffset();
    }

    @Override
    public Integer getTotal() {
        throw new RuntimeException("total not support");
        // return this.getOffset();
    }

    public void setShowTotal(boolean isShowTotal) {
        this.isShowTotal = isShowTotal;
    }

    @Override
    public boolean isShowTotal() {
        return this.isShowTotal;
    }
}