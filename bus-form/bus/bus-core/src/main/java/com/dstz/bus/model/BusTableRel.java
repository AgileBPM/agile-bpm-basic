package com.dstz.bus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dstz.bus.api.model.IBusTableRel;

import cn.hutool.core.collection.CollectionUtil;

/**
 * <pre>
 * 业务表对象的关联对象
 * </pre>
 *
 * @author aschs
 *
 */
public class BusTableRel implements Serializable, IBusTableRel {
	/**
	 * 子级
	 */
	private List<BusTableRel> children;
	/**
	 * 业务表的key
	 */
	private String tableKey;
	/**
	 * 业务表的描述
	 */
	private String tableComment;
	/**
	 * 类型 枚举 BusTableRelType
	 */
	private String type;
	/**
	 * 外键设置
	 */
	private List<BusTableRelFk> fks;

	// 以下字段不入库
	/**
	 * 业务表
	 */
	private BusinessTable table;
	/**
	 * 父对象
	 */
	private BusTableRel parent;
	/**
	 * 所属的bo
	 */
	private BusinessObject busObj;

	@Override
	public List<BusTableRel> getChildren() {
		if (children == null) {
			return Collections.emptyList();
		}
		return children;
	}

	// 获取某类型的子表
	// oneToOne or oneToMany
	@Override
	public List<IBusTableRel> getChildren(String type) {
		List<IBusTableRel> list = new ArrayList<>();
		if (CollectionUtil.isNotEmpty(children))
			for (BusTableRel rel : children) {
				if (type.equals(rel.getType())) {
					list.add(rel);
				}
			}
		return list;
	}

	public void setChildren(List<BusTableRel> children) {
		this.children = children;
	}

	@Override
	public String getTableKey() {
		return tableKey;
	}

	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
	}

	@Override
	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public List<BusTableRelFk> getFks() {
		return fks;
	}

	public void setFks(List<BusTableRelFk> fks) {
		this.fks = fks;
	}

	@Override
	public BusTableRel find(String tableKey) {
		if (this.tableKey.equals(tableKey)) {
			return this;
		}
		if (this.children != null) {
			for (BusTableRel rel : this.children) {
				BusTableRel r = rel.find(tableKey);
				if (r != null) {
					return r;
				}
			}
		}
		return null;
	}

	@Override
	public List<BusTableRel> list() {
		List<BusTableRel> rels = new ArrayList<>();
		rels.add(this);
		if (children != null) {
			for (BusTableRel rel : children) {
				rels.addAll(rel.list());
			}
		}
		return rels;
	}

	@Override
	public BusinessTable getTable() {
		return table;
	}

	public void setTable(BusinessTable table) {
		this.table = table;
	}

	@Override
	public BusTableRel getParent() {
		return parent;
	}

	public void setParent(BusTableRel parent) {
		this.parent = parent;
	}

	@Override
	public BusinessObject getBusObj() {
		return busObj;
	}

	public void setBusObj(BusinessObject busObj) {
		this.busObj = busObj;
	}

}
