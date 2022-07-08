package com.dstz.form.model.design;

import java.util.ArrayList;
import java.util.List;

import com.dstz.base.core.util.StringUtil;
import com.dstz.bus.api.model.IBusTableRel;
import com.dstz.bus.api.model.IBusinessColumn;

public class FormGroup {
	private String comment;
	private String key;
	private IBusTableRel tableRelation;
	private List<FormColumn> columnList;
	
	public boolean hasTitle() {
		return StringUtil.isNotEmpty(comment);
	}
	
	/**
	 * 获取当前组一对一子表的一对多子表。 一对一的一对一暂不支持吧。后面在写递归
	 * @return
	 */
	public List<IBusTableRel> getOne2OneChildsOne2ManyRelations(){
		List<IBusTableRel> grandson = new ArrayList<IBusTableRel>();
		// 获取一对一子表
		tableRelation.getChildren("oneToOne").forEach(one2One->{
			grandson.addAll(one2One.getChildren("oneToMany"));
		});
		return grandson;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<FormColumn> getColumnList() {
		return columnList;
	}

	public IBusTableRel getTableRelation() {
		return tableRelation;
	}

	public void setTableRelation(IBusTableRel tableRelation) {
		this.tableRelation = tableRelation;
	}

	public void setColumnList(List<FormColumn> columnList) {
		this.columnList = columnList;
	}

}
