package com.dstz.form.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <pre>
 * 描述：表单定义的页面数据类
 * 当流程页时，请继承当前类，然后多一些字段，例如按钮数据什么的
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年4月14日 下午5:38:51
 * 版权:summer
 * </pre>
 */
@ApiModel(description="表单定义的页面数据类")
public class FormDefData implements Serializable {
	/**
	 * <pre>
	 * 表单的数据
	 * bo下面的第一层默认是主表的字段（不需要mainTable:{}这一层）
	 * 子表如果是一对一，那么是{}对象，第一次取数据时，需要被初始化
	 * 子表是一对多时，那么是[]数组，第一次取数据时不需要初始化，名字默认+List
	 * {
	 * 	bo1:{
	 *		column1:1,
	 *		column2:2,
	 *		table2:[{
	 *				column3:3,
	 *				column4:4
	 *				table3:[{
	 *					column5:5,
	 *					column6:6
	 *				}]
	 *			},...
	 *		],
	 *		table4:{...},
	 *  }
	 * }
	 * </pre>
	 */
    @ApiModelProperty("表单的业务数据 JSON")
	private JSONObject data;
	/**
	 * <pre>
	 * 表单的初始化数据
	 * 
	 * {
	 * 	bo1:{
	 * 		column1:1,
	 *		column2:2,
	 *		table1:{
	 *			column3:1,
	 *			column4:2
	 *			table2List:[{
	 *				column5:
	 *				column6:
	 *			}]
	 *	 	}
	 *  }
	 * }
	 * </pre>
	 */
    @ApiModelProperty("表单的初始化数据")
	private JSONObject initData;
	/**
	 * <pre>
	 *  表单关于字段的权限
	 * {
	 * 	bo1:{
	 *		table1:{
	 *			column1:w,
	 *			column2:r
	 *	 	}
	 *  }
	 * }
	 * </pre>
	 */
    @ApiModelProperty("表单字段的权限")
	private JSONObject permission;
	/**
	 * <pre>
	 * 表单关于表的权限
	 * {
	 * 	bo1:{
	 *		table1:w
	 *  }
	 * }
	 * </pre>
	 */
    @ApiModelProperty("表单的表权限")
	private JSONObject tablePermission;
	/**
	 * 页面html
	 */
    @ApiModelProperty("表单的HTML 源码")
	private String html;
	
	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public JSONObject getInitData() {
		return initData;
	}

	public void setInitData(JSONObject initData) {
		this.initData = initData;
	}

	public JSONObject getPermission() {
		return permission;
	}

	public void setPermission(JSONObject permission) {
		this.permission = permission;
	}

	public JSONObject getTablePermission() {
		return tablePermission;
	}

	public void setTablePermission(JSONObject tablePermission) {
		this.tablePermission = tablePermission;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}
