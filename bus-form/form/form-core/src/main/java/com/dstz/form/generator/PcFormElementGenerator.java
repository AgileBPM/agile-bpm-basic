package com.dstz.form.generator;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.core.util.ThreadMapUtil;
import com.dstz.bus.api.constant.BusTableRelType;
import com.dstz.bus.api.model.IBusTableRel;
import com.dstz.bus.api.model.IBusinessColumn;

/**
 * 自定义表单控件生成器<br>
 * input select radio dic 等等的生成<br>
 * @author jeff
 */
@Component
public class PcFormElementGenerator extends AbsFormElementGenerator{

	private void handleNgModel(Element element,IBusinessColumn column) {
		String boCode = (String) ThreadMapUtil.get("boCode");
		IBusTableRel relation  = (IBusTableRel) ThreadMapUtil.get("relation");
		//如果是多行子表
		if(relation.getType().equals(BusTableRelType.ONE_TO_MANY.getKey())) {
			element.attr("ng-model",column.getTable().getKey() + "." + column.getKey());
			return ;
		}
		
		element.attr("ng-model",getScopePath(relation)+ "." + column.getKey());
		
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
	
	
	protected String getColumnOnetext(IBusinessColumn column) {
		
		Element element = getElement("input").attr("type", "text").addClass("form-control");
		
		handleNgModel(element, column);
		handlePermission(element, column);
		handleValidateRules(element, column);
		
		return element.toString();
	}
	

	protected String getColumnDate(IBusinessColumn column) {
		Element element = getElement("input").addClass("form-control");
		
		handleNgModel(element, column);
		handleValidateRules(element, column);
		handlePermission(element, column);
		
		String configStr = column.getCtrl().getConfig();
		if(StringUtil.isEmpty(configStr)) {
			throw new BusinessException(String.format("表【%s】日期字段  【%s】解析失败,配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		element.attr("ab-date",JSON.parseObject(configStr).getString("format"));
		
		return element.toString();
	}
	
	protected String getColumnDic(IBusinessColumn column) {
		Element element = getElement("span").attr("type", "text").addClass("input-div");
		
		handleNgModel(element, column);
		handleValidateRules(element, column);
		handlePermission(element, column);
		
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("key")) {
			throw new BusinessException(String.format("表【%s】数据字典  字段【%s】解析失败,alias 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		element.attr("ab-combox", element.attr("ng-model"));
		element.attr("dict-key", config.getString("key"));
		
		return element.toString();
	}

	protected String getColumnIdentity(IBusinessColumn column) {
		Element element = getElement("input").attr("type", "text").addClass("form-control");
		handleNgModel(element, column);
		handlePermission(element, column);
		handleValidateRules(element, column);
		
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("alias")) {
			throw new BusinessException(String.format("表【%s】流水号  字段【%s】解析失败,alias 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		element.attr("ab-serial-no", config.getString("alias"));
		return element.toString();
	}

	protected String getColumnMultitext(IBusinessColumn column) {
		Element element = getElement("textarea").attr("type", "text").addClass("form-control");
		
		handleNgModel(element, column);
		handlePermission(element, column);
		handleValidateRules(element, column);
		
		return element.toString();
	}

	protected String getColumnCheckBox(IBusinessColumn column) {
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("options")) {
			throw new BusinessException(String.format("表【%s】CheckBox 字段  【%s】解析失败,options 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		Element permissionElement = getElement("div");
		handleNgModel(permissionElement, column); 
		permissionElement.attr("ab-checked-permission", getPermissionPath(column));
		handleValidateRules(permissionElement, column);
		
		JSONArray options = config.getJSONArray("options");
		for (int i = 0; i < options.size(); i++) {
			JSONObject option = options.getJSONObject(i);
			
			Element element = permissionElement.appendElement("label").addClass("checkbox-inline");
			Element child  = element.appendElement("input").attr("type", "checkbox");
			child.attr("ab-checkbox", "");
			handleNgModel(child, column);
			child.attr("value", option.getString("key"));
			element.appendText(option.getString("txt"));
		}
		
		return permissionElement.toString();
	}

	protected String getColumnRadio(IBusinessColumn column) {
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("options")) {
			throw new BusinessException(String.format("表【%s】Radio 字段  【%s】解析失败,options 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		Element permissionElement = getElement("div");
		handleNgModel(permissionElement, column);
		permissionElement.attr("ab-checked-permission",  getPermissionPath(column));
		handleValidateRules(permissionElement, column);
		
		JSONArray options = config.getJSONArray("options");
		for (int i = 0; i < options.size(); i++) {
			JSONObject option = options.getJSONObject(i);
			
			Element element = permissionElement.appendElement("label").addClass("radio-inline");
			Element child  = element.appendElement("input").attr("type", "radio");
			handleNgModel(child, column);
			child.attr("value", option.getString("key"));
			element.appendText(option.getString("txt"));
		}
		
		return permissionElement.toString();
	}

	protected String getColumnSelect(IBusinessColumn column,Boolean isMultiple) {
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("options")) {
			throw new BusinessException(String.format("表【%s】Select 字段  【%s】解析失败,options 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		Element permissionElement = getElement("select").addClass("form-control");
		handleNgModel(permissionElement, column);
		permissionElement.attr("ab-checked-permission",getPermissionPath(column));
		handleValidateRules(permissionElement, column);
		
		if(isMultiple) {
			permissionElement.attr("multiple", "true");
			permissionElement.attr("ab-array-str","");
		}
		
		JSONArray options = config.getJSONArray("options");
		for (int i = 0; i < options.size(); i++) {
			JSONObject option = options.getJSONObject(i);
			
			Element element = permissionElement.appendElement("option");
			element.attr("value", option.getString("key"));
			element.text(option.getString("txt"));
		}
		
		return permissionElement.toString();
	}

	protected String getColumnFile(IBusinessColumn column) {
		//<a href="javascript:void(0)" class="btn btn-primary fa-upload" ab-upload ng-model="test">指令测试</a>
		Element element = getElement("a").attr("href", "javascript:void(0)").addClass("btn btn-primary fa-upload");
		element.attr("ab-upload","");
		handleNgModel(element, column);
		element.attr("ab-edit-permission",getPermissionPath(column));
		
		return element.toString();
	}
	
	// id="boCode-tableKey" ab-basic-permission="boCode.table.tableKey" ...
	public String getSubAttrs(IBusTableRel rel) {
		StringBuilder sb = new StringBuilder();
		sb.append( " id="+"\""+ rel.getBusObj().getKey()+"-"+rel.getTableKey()+"\" ");
		
		//一对多的三层情况下弹框展示
		if(rel.getType().equals(BusTableRelType.ONE_TO_MANY.getKey()) 
				&& !rel.getParent().getType().equals(BusTableRelType.MAIN.getKey())) {
			sb.append(" hide ");
		}
		
		sb.append(" table-key=\""+rel.getTableKey()+"\" ");
		
		return sb.toString() ;
	}


	@Override
	public String getGeneratorName() {
		return "generator";
	}
}
