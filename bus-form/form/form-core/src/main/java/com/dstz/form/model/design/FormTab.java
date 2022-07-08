package com.dstz.form.model.design;

import java.util.List;

public class FormTab {
	private String comment;
	private List<FormGroup> groupList;
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<FormGroup> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<FormGroup> groupList) {
		this.groupList = groupList;
	}
}
