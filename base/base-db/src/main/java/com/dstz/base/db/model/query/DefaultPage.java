package com.dstz.base.db.model.query;

import com.dstz.base.api.Page;
import com.dstz.base.api.query.FieldSort;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分页查询对象
 */
public class DefaultPage extends RowBounds implements Page, Serializable {
    private static final long serialVersionUID = -1718110184113189225L;
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
    protected List<FieldSort> orders = new ArrayList<FieldSort>();
    /**
     * 结果集是否包含TotalCount
     */
    protected boolean containsTotalCount = true;

    protected Boolean asyncTotalCount;

    private boolean isShowTotal = true;

    public DefaultPage() {
    }

    public DefaultPage(RowBounds rowBounds) {
        if (rowBounds instanceof DefaultPage) {
            DefaultPage pageBounds = (DefaultPage) rowBounds;
            this.pageNo = pageBounds.pageNo;
            this.pageSize = pageBounds.pageSize;
            this.orders = pageBounds.orders;
            this.containsTotalCount = pageBounds.containsTotalCount;
            this.asyncTotalCount = pageBounds.asyncTotalCount;
        } else {
            this.pageNo = (rowBounds.getOffset() / rowBounds.getLimit()) + 1;
            this.pageSize = rowBounds.getLimit();
        }

    }

    /**
     * Query TOP N, default containsTotalCount = false
     *
     * @param limit
     */
    public DefaultPage(int limit) {
        this.pageSize = limit;
        this.containsTotalCount = false;
    }

    public DefaultPage(int page, int limit) {
        this(page, limit, new ArrayList<FieldSort>(), true);
    }

    /**
     * Just sorting, default containsTotalCount = false
     *
     * @param orders
     */
    public DefaultPage(List<FieldSort> orders) {
        this(NO_PAGE, NO_ROW_LIMIT, orders, false);
    }

    /**
     * Just sorting, default containsTotalCount = false
     *
     * @param order
     */
    public DefaultPage(FieldSort... order) {
        this(NO_PAGE, NO_ROW_LIMIT, order);
        this.containsTotalCount = false;
    }

    public DefaultPage(int page, int limit, FieldSort... order) {
        this(page, limit, Arrays.asList(order), true);
    }

    public DefaultPage(int page, int limit, List<FieldSort> orders) {
        this(page, limit, orders, true);
    }

    public DefaultPage(int page, int limit, List<FieldSort> orders, boolean containsTotalCount) {
        this.pageNo = page;
        this.pageSize = limit;
        this.orders = orders;
        this.containsTotalCount = containsTotalCount;
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

    public boolean isContainsTotalCount() {
        return containsTotalCount;
    }

    public void setContainsTotalCount(boolean containsTotalCount) {
        this.containsTotalCount = containsTotalCount;
    }

    public List<FieldSort> getOrders() {
        List<FieldSort> list = orders;
        return list;
    }

    public void setOrders(List<FieldSort> orders) {
        this.orders = orders;
    }

    public Boolean getAsyncTotalCount() {
        return asyncTotalCount;
    }

    public void setAsyncTotalCount(Boolean asyncTotalCount) {
        this.asyncTotalCount = asyncTotalCount;
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
        final StringBuilder sb = new StringBuilder("PageBounds{");
        sb.append("page=").append(pageNo);
        sb.append(", limit=").append(pageSize);
        sb.append(", orders=").append(orders);
        sb.append(", containsTotalCount=").append(containsTotalCount);
        sb.append(", asyncTotalCount=").append(asyncTotalCount);
        sb.append('}');
        return sb.toString();
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