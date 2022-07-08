package com.dstz.form.generator;

import java.awt.color.ICC_ColorSpace;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.core.util.ThreadMapUtil;
import com.dstz.bus.api.constant.BusColumnCtrlType;
import com.dstz.bus.api.constant.BusTableRelType;
import com.dstz.bus.api.model.IBusTableRel;
import com.dstz.bus.api.model.IBusinessColumn;
import com.dstz.form.api.constant.FormStatusCode;
import com.dstz.form.model.design.FormColumn;
import com.dstz.form.model.design.FormGroup;
/**
 * 自定义表单控件生成器父类，提供生成表单通用公共方法<br>
 * @author jeff
 */
public abstract class AbsFormElementGenerator {
	protected  Logger LOG = LoggerFactory.getLogger(getClass());

	public abstract String getGeneratorName();
	
	/**
	 * 获取字段的html内容
	 * @param column
	 * @param relation
	 * @return HTML
	 */
	public String getColumn(IBusinessColumn column,IBusTableRel relation) {
		if(column == null ) {
			throw new BusinessException(String.format("%s 生成表单异常，column 为 null",relation != null? relation.getTableComment():"")) ;
		}
		String boCode = relation.getBusObj().getKey();
		ThreadMapUtil.put("boCode", boCode);
		ThreadMapUtil.put("relation", relation);
		
		if(column.getCtrl()== null) {
			LOG.debug(" column [{}]ctrl 配置为空，默认生成 input框，表：{}",column.getComment(),column.getTable().getComment());
			return  getColumnOnetext(column);
		}
		
		BusColumnCtrlType columnType = BusColumnCtrlType.getByKey(column.getCtrl().getType());
		
		try {
			
			switch (columnType) {
			
			case ONETEXT: return getColumnOnetext(column);
			
			case DATE: return getColumnDate(column);
			
			case DIC: return getColumnDic(column);
			
			case SERIALNO: return getColumnIdentity(column);
			
			case MULTITEXT: return getColumnMultitext(column);
			
			case CHECKBOX: return getColumnCheckBox(column);
			
			//case MULTISELECT: return getColumnSelect(column,true);	
			
			case RADIO: return getColumnRadio(column);	
			
			case SELECT: return getColumnSelect(column,false);	
			
			case FILE: return getColumnFile(column);
			
			default: return "";
			
			}
			
		} catch (Exception e) {
			throw new BusinessException(String.format("表单字段 [%s-%s]解析失败 ,控件类型[%s] :%s",column.getTable().getName(),column.getComment(), columnType.getDesc(),e.getMessage())
					,FormStatusCode.FORM_ELEMENT_GENERATOR_ERROR,e);
		}
	}
	
	public String getColumn(FormGroup group,FormColumn formColumn) {
		IBusTableRel tableRel = group.getTableRelation();
		
		// 一对一子表 的TableRelation 需要从父表中获取
		if(StringUtil.isNotEmpty(formColumn.getTableKey())&& !formColumn.getTableKey().equals(tableRel.getTableKey())) {
			IBusTableRel table = tableRel.find(formColumn.getTableKey());
			if(table != null) {
				tableRel = table;
			}
		}
		
		if(!formColumn.getTableKey().equals(tableRel.getTableKey())) {
			LOG.error("生成表单可能存在异常！formColumnTableKey{},tableRelTableKey{}",formColumn.getTableKey(),tableRel.getTableKey());
		}
		
	    IBusinessColumn businessColumn = tableRel.getTable().getColumnByKey(formColumn.getKey());
		
	    if(businessColumn == null) {
	    	LOG.error("布局模板查找Column配置失败！字段：{}，表：{}",formColumn.getComment(),formColumn.getTableKey());
	    }
	    
		return this.getColumn(businessColumn, tableRel);
	}

	protected abstract String getColumnOnetext(IBusinessColumn column);
	
	protected abstract String getColumnDate(IBusinessColumn column);
	
	protected abstract String getColumnDic(IBusinessColumn column);
	
	protected abstract String getColumnIdentity(IBusinessColumn column);
	
	protected abstract String getColumnMultitext(IBusinessColumn column);
	
	protected abstract String getColumnCheckBox(IBusinessColumn column);
	
	protected abstract String getColumnRadio(IBusinessColumn column);
	
	protected abstract String getColumnSelect(IBusinessColumn column,Boolean isMultiple);
	
	protected abstract String getColumnFile(IBusinessColumn column);
	
	/**
	 * 构建一个 element
	 * @param type
	 * @return
	 */
	protected Element getElement(String type) {
		Document doc = Jsoup.parse("");
		Element element = doc.createElement(type);
		return element;
	}
	/**
	 * 权限指令
	 * @param element
	 * @param column
	 */
	protected void handlePermission(Element element,IBusinessColumn column) {
		element.attr("ab-basic-permission", getPermissionPath(column));
		element.attr("desc", column.getComment());
	}
	
	public String getPermissionPath(IBusinessColumn column,IBusTableRel relation) {
		String boCode = relation.getBusObj().getKey();
		return "permission."+ boCode + "." + column.getTable().getKey() + "." + column.getKey();
	}
	
	/**
	 * 权限路径
	 * @param column
	 * @return
	 */
	protected String getPermissionPath(IBusinessColumn column) {
		String boCode = (String) ThreadMapUtil.get("boCode");
		return "permission."+ boCode + "." + column.getTable().getKey() + "." + column.getKey();
	}
	
	/**
	 * 校验指令
	 * @param element
	 * @param column
	 */
	protected void handleValidateRules(Element element,IBusinessColumn column) {
		if(column.getCtrl() == null) {
			element.attr("ab-validate", "{}");
			return;
		}
		
		String rulesStr = column.getCtrl().getValidRule();
		JSONObject validateRule = new JSONObject();
		
		if(StringUtil.isNotEmpty(rulesStr)) {
			JSONArray rules = JSONArray.parseArray(rulesStr);
			//[{"name":"time","title":"时间"},{"name":"required","title":"必填"}]
			// to {time:true,required:true}
			for (int i = 0; i < rules.size(); i++) {
				JSONObject rule = rules.getJSONObject(i);
				
				validateRule.put(rule.getString("name"), true);
			}
			
			if (column.isRequired()) {
				validateRule.put("required", true);
			}
			if(column.getLength()>1) {
				validateRule.put("maxlength", column.getLength());
			}
		}
		
		element.attr("ab-validate", validateRule.toJSONString());
		
		IBusTableRel relation = (IBusTableRel) ThreadMapUtil.get("relation");
		//为了validateRule提示
		element.attr("desc",relation.getTableComment() + "-" + column.getComment());
	}
	
	
	/**
	 * <pre>
	 * 获取子表的路径
	 * 一直向上递归、若上级为主表、或者一对多的子表则停止。
	 * eg: mian.subaList[1].subbList[1].name  那subb的path为 subbList
	 * eg: mian.suba.subbList[1].name 那subb的path 为 main.suba.subbList
	 * eg: main.subaList[1].subb.name 那 subb的path 为 suba.subb.name
	 * eg: main.suba.subb.name 那subb的path为 mian.suba.subb.name
	 * 子表会存在单独作用域所以查询到子表那里即可
	 * </pre>
	 * @param relation
	 * @return
	 */
	public String getScopePath(IBusTableRel relation) {
		if(relation.getType().equals(BusTableRelType.MAIN.getKey())) {
			return "data."+relation.getBusObj().getKey();
		}
		
		StringBuilder sb = new StringBuilder();
		// 一对一是对象名字 
		sb.append(relation.getTableKey());
		// 如果是一对多则添加List
		if(relation.getType().equals(BusTableRelType.ONE_TO_MANY.getKey())){
			sb.append("List");
			//第三层后面均为表单模板，模板会从subTempData中获取中间值。
			if(isThreeChildren(relation)) {
				sb.insert(0, "subTempData.");
				return sb.toString();
			}
		}
		
		
		getParentPath(relation.getParent(),sb);
		
		return sb.toString();
	}
	
	protected void getParentPath(IBusTableRel parent,StringBuilder sb) {
		if(parent == null) return;
		//上级是一对多则将scope的name 返回
		if(parent.getType().equals(BusTableRelType.ONE_TO_MANY.getKey())) {
			sb.insert(0, parent.getTableKey()+".");
			return ;
		}
		//一对一子表
		if(parent.getType().equals(BusTableRelType.ONE_TO_ONE.getKey())) {
			sb.insert(0, parent.getTableKey()+".");
		}
		// 主表则是boCode
		if(parent.getType().equals(BusTableRelType.MAIN.getKey())) {
			sb.insert(0, "data."+parent.getBusObj().getKey()+".");
		}
		
		getParentPath(parent.getParent(), sb);
	}
	
	// id="boCode-tableKey" ab-basic-permission="boCode.table.tableKey" ...
	public abstract String getSubAttrs(IBusTableRel rel) ;
	
	//如果父类不是主表、那么当前子表则一定是第三层
	public boolean isThreeChildren(IBusTableRel rel) {
		if(rel.getType().equals(BusTableRelType.ONE_TO_MANY.getKey()) 
				&& !rel.getParent().getType().equals(BusTableRelType.MAIN.getKey())) {
			return true;
		}
		return false;
	}
	
	/**
	 * <pre>
	 * 第三层需要一个v-if="subTempData.xxx"
	 * 不然vue编译会报错
	 * </pre>	
	 * @param relation
	 * @return
	 */
	public String getDivVIf(IBusTableRel relation) {
		if(isThreeChildren(relation)) {
			return "v-if=\"subTempData."+relation.getParent().getTableKey()+"\"";
		}
		return "";
	}
	
	protected void handleElementPlaceHolder(IBusinessColumn column,Element element) {
		if(column.getCtrl() ==null) return;
		
		//添加上  placeholder 的支持
		String configStr = column.getCtrl().getConfig();
		if (StringUtil.isEmpty(configStr)) {
			return;
		}

		JSONObject config = JSON.parseObject(configStr);
		Boolean placeholder = config.getBoolean("placeholder");
		
		if(placeholder == null || !placeholder )return ;
		element.attr("placeholder", config.getString("placeholderText"));
	}
}
