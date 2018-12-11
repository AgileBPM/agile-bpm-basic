package com.dstz.base.rest;

import com.alibaba.fastjson.JSON;
import com.dstz.base.api.query.Direction;
import com.dstz.base.api.query.FieldSort;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.query.DefaultFieldSort;
import com.dstz.base.db.model.query.DefaultPage;
import com.dstz.base.db.model.query.DefaultQueryFilter;
import com.dstz.base.rest.util.RequestUtil;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GenericController {
	protected  Logger LOG = LoggerFactory.getLogger(getClass());
	
	   protected <T> ResultMsg<T> getSuccessResult(T data, String msg){
	        ResultMsg<T> resultMsg = new ResultMsg<T>();
	        resultMsg.IsOk(true);
	        resultMsg.setMsg(msg);
	        resultMsg.setData(data);
	        return resultMsg;
	    }
	    
	    protected <T> ResultMsg<T>  getSuccessResult(T data) {
	        ResultMsg<T> resultMsg = new ResultMsg<T>();
	        resultMsg.IsOk(true);
	        resultMsg.setMsg("请求成功");
	        resultMsg.setData(data);
	        return resultMsg;
	    }

	    
	    protected ResultMsg<String> getSuccessResult(String msg) {
	        ResultMsg<String> resultMsg = new ResultMsg<String>();
	        resultMsg.IsOk(true);
	        resultMsg.setMsg(msg);
	        return resultMsg;
	    }

	   
	    /**
	     * 返回构建的QueryFilter
	     *
	     * @param request
	     * @return QueryFilter
	     * @throws
	     * @since 1.0.0
	     */
	    protected QueryFilter getQueryFilter(HttpServletRequest request) {
	        DefaultQueryFilter queryFilter = new DefaultQueryFilter();
	        try {
	        	RequestUtil.handleRequestParam(request, queryFilter);
	        	
	            String offset = request.getParameter("offset");
	            String limit = request.getParameter("limit");
	            if (StringUtil.isNotEmpty(offset) && StringUtil.isNotEmpty(limit)) {
	                RowBounds rowBounds = new RowBounds(Integer.parseInt(offset), Integer.parseInt(limit));
	                DefaultPage page = new DefaultPage(rowBounds);
	                queryFilter.setPage(page);
	            }
	            
	            // 设置排序字段
	            String sort = request.getParameter("sort");
	            String order = request.getParameter("order");
	            if (StringUtil.isNotEmpty(sort) && StringUtil.isNotEmpty(order)) {
	                List<FieldSort> fieldSorts = new ArrayList<FieldSort>();
	                fieldSorts.add(new DefaultFieldSort(sort, Direction.fromString(order)));
	                queryFilter.setFieldSortList(fieldSorts);
	            }
	        } catch (Exception e) {
	        }
	        return queryFilter;
	    }
	    
	    
	    
	    
	  // ====================下面方法不在推荐使用=============================
	    
	    /**
	     * 向 response 中写入JSON数据
	     *
	     * @param response
	     * @param data
	     * @throws IOException
	     */
	    @SuppressWarnings({"rawtypes", "unchecked"})
	    @Deprecated
	    protected void writeSuccessData(HttpServletResponse response, Object data) throws IOException {
	        ResultMsg resultMsg = new ResultMsg();
	        resultMsg.IsOk(true);
	        resultMsg.setData(data);
	        response.getWriter().print(JSON.toJSONString(resultMsg));
	    }
	    
	    @Deprecated
	    protected void writeSuccessData(HttpServletResponse response, Object data, String msg) throws IOException {
	        ResultMsg resultMsg = new ResultMsg();
	        resultMsg.IsOk(true);
	        resultMsg.setMsg(msg);
	        resultMsg.setData(data);
	        response.getWriter().print(JSON.toJSONString(resultMsg));
	    }
	    @Deprecated
	    protected void writeSuccessResult(HttpServletResponse response, String msg) throws IOException {
	        ResultMsg resultMsg = new ResultMsg();
	        resultMsg.IsOk(true);
	        resultMsg.setMsg(msg);
	        response.getWriter().print(JSON.toJSONString(resultMsg));
	    }
	    /**
	     * 返回出错或成功的信息。
	     *
	     * @param writer
	     * @param resultMsg
	     */
	    @Deprecated
	    protected void writeResultMessage(PrintWriter writer, ResultMsg resultMsg) {
	        writer.print(JSON.toJSONString(resultMsg));
	    }
	    
}