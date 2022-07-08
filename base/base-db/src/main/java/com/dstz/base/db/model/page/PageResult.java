package com.dstz.base.db.model.page;

import com.dstz.base.api.response.impl.BaseResult;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@SuppressWarnings("rawtypes")
@ApiModel(description="通用分页结果包装类")
public class PageResult<T> extends BaseResult {
	private static final long serialVersionUID = 1L;
	// 总记录数
    @ApiModelProperty("分页大小")
    private Integer pageSize = 0;
    @ApiModelProperty("当前页")
    private Integer page = 1;
    @ApiModelProperty("总条数")
    private Integer total = 0;

    // 行记录
    @ApiModelProperty("分页列表数据")
    private List rows = null;

    public PageResult() {

    }

    public PageResult(List<T> rows, Integer total) {
        this.rows = rows;
        this.total = total;
    }

    public PageResult(List<T> arrayList) {
        this.rows = arrayList;

        //适配 pagehelper 的 page
        if (arrayList instanceof Page) {
            Page pageList = (Page) arrayList;
            Integer total = new Long(pageList.getTotal()).intValue();
            this.pageSize = pageList.getPageSize();
            this.setPage(pageList.getPages());
            this.setTotal(total);

        } else {
            this.total = arrayList.size();
        }
        this.setOk(Boolean.TRUE);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}
