package com.dstz.form.generator;

import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

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

/**
 * 自定义表单控件生成器<br>
 * input select radio dic 等等的生成<br>
 * @author jeff
 */
@Component
public class MobileFormElementGenerator extends AbsFormElementGenerator{
	public String getColumn(IBusinessColumn column,IBusTableRel relation) {
		if("1".equals("1")) {
			return super.getColumn(column, relation);
		}
		
		BusColumnCtrlType columnType = BusColumnCtrlType.getByKey(column.getCtrl().getType());
		String boCode = relation.getBusObj().getKey();
		ThreadMapUtil.put("boCode", boCode);
		ThreadMapUtil.put("relation", relation);
		
		return getColumnOnetext(column);
		
	}
	
	
	private void handleVModel(Element element,IBusinessColumn column) {
		String boCode = (String) ThreadMapUtil.get("boCode");
		IBusTableRel relation  = (IBusTableRel) ThreadMapUtil.get("relation");
		//如果是多行子表
		if(relation.getType().equals(BusTableRelType.ONE_TO_MANY.getKey())) {
			element.attr("v-model",column.getTable().getKey() + "." + column.getKey());
			return ;
		}
		
		element.attr("v-model",getScopePath(relation)+ "." + column.getKey());
	}
 
	
	protected String getColumnOnetext(IBusinessColumn column) {
		
		Element element = getElement("input").attr("type", "text").addClass("weui-input");
		
		handleVModel(element, column);
		handlePermission(element, column);
		handleValidateRules(element, column);
		
		return element.toString();
	}
	
	//<datetime v-model="data.kjcs.sr" format="YYYY-MM-DD HH:mm:ss" ab-validate="{&quot;date&quot;:true}" desc="生日" v-ab-permission="permission.kjcs.cskj.sr"></datetime>
	protected String getColumnDate(IBusinessColumn column) {
		Element element = getElement("datetime");
		
		handleVModel(element, column);
		handleValidateRules(element, column);
		handlePermission(element, column);
		
		String configStr = column.getCtrl().getConfig();
		if(StringUtil.isEmpty(configStr)) {
			throw new BusinessException(String.format("表【%s】日期字段  【%s】解析失败,配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		String format = JSON.parseObject(configStr).getString("format");
		//yyyy-MM-dd HH:mm:ss  pc 移动端为：YYYY-MM-DD HH:mm
		element.attr("format",format.replace("yyyy", "YYYY").replace("dd", "DD").replace(":ss", ""));
		
		return element.toString();
	}
	
	protected String getColumnDic(IBusinessColumn column) {
		Element element = getElement("ab-dict").attr("type", "text").addClass("input-div");
		
		handleVModel(element, column);
		handleValidateRules(element, column);
		handlePermission(element, column);
		
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("key")) {
			throw new BusinessException(String.format("表【%s】数据字典  字段【%s】解析失败,alias 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		element.attr("dict-key", config.getString("key"));
		
		return element.toString();
	}

	protected String getColumnIdentity(IBusinessColumn column) {
		Element element = getElement("input").attr("type", "text").addClass("weui-input");
		handleVModel(element, column);
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
		Element element = getElement("textarea").attr("type", "text").addClass("weui-textarea");
		
		handleVModel(element, column);
		handlePermission(element, column);
		handleValidateRules(element, column);
		
		return element.toString();
	}

	/**
	 * <ab-checkbox v-model="data.kjcs.ah" v-ab-permission="permission.kjcs.cskj.ah" v-ab-validate="{}" desc="爱好">
            <div slot-scope="scope">
                <label class=" checkbox-inline"><input type="checkbox" name="testttt" v-model="scope.tempData.currentValue" value="y"/>羽毛器</label>
                <label class=" checkbox-inline"><input name="testttt" type="checkbox" v-model="scope.tempData.currentValue" value="l"/>篮球</label>
                <label class=" checkbox-inline"><input type="checkbox" name="testttt" v-model="scope.tempData.currentValue" value="s"/>游泳</label>
            </div>
      </ab-checkbox>
	 */
	protected String getColumnCheckBox(IBusinessColumn column) {
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("options")) {
			throw new BusinessException(String.format("表【%s】CheckBox 字段  【%s】解析失败,options 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		Element checkbox = getElement("ab-checkbox");
		handleVModel(checkbox, column); 
		handleValidateRules(checkbox, column);
		handlePermission(checkbox,column);
		Element template = checkbox.appendElement("div").attr("slot-scope", "scope");
		
		JSONArray options = config.getJSONArray("options");
		for (int i = 0; i < options.size(); i++) {
			JSONObject option = options.getJSONObject(i);
			
			Element element = template.appendElement("label").addClass("checkbox-inline");
			Element child  = element.appendElement("input").attr("type", "checkbox");
			child.attr("v-model", "scope.tempData.currentValue");
			child.attr("value", option.getString("key"));
			child.attr("name",String.format("%s-%s-%s",(String) ThreadMapUtil.get("boCode"), column.getTable().getKey(),column.getName()));
			handlePermission(child,column);
			element.appendText(option.getString("txt"));
		}
		
		return checkbox.toString();
	}

	protected String getColumnRadio(IBusinessColumn column) {
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("options")) {
			throw new BusinessException(String.format("表【%s】Radio 字段  【%s】解析失败,options 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		Element permissionElement = getElement("div");
		JSONArray options = config.getJSONArray("options");
		for (int i = 0; i < options.size(); i++) {
			JSONObject option = options.getJSONObject(i);
			
			Element label = permissionElement.appendElement("label").addClass("radio-inline");
			Element child  = label.appendElement("input").attr("type", "radio");
			
			handleVModel(child, column);
			child.attr("v-ab-permission",  getPermissionPath(column));
			handleValidateRules(child, column);
			
			child.attr("value", option.getString("key"));
			label.appendText(option.getString("txt"));
			child.attr("name",String.format("%s-%s-%s",(String) ThreadMapUtil.get("boCode"), column.getTable().getKey(),column.getName()));
		}
		
		return permissionElement.toString();
	}
	

	protected String getColumnSelect(IBusinessColumn column,Boolean isMultiple) {
		JSONObject config = JSON.parseObject(column.getCtrl().getConfig());
		if(!config.containsKey("options")) {
			throw new BusinessException(String.format("表【%s】Select 字段  【%s】解析失败,options 配置信息不能为空", column.getTable().getKey(),column.getComment()));
		}
		
		Element permissionElement = getElement("select").addClass("weui-input");
		handleVModel(permissionElement, column);
		permissionElement.attr("v-ab-permission",getPermissionPath(column));
		handleValidateRules(permissionElement, column);
		
		if(isMultiple) {
			permissionElement.attr("multiple", "true");
		}
		
		Element select = permissionElement.appendElement("option");
		select.attr("value", "");
		select.text("请选择");
		
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
	//	handleVModel(element, column);
		element.attr("v-ab-permission:edit",getPermissionPath(column));
		
		return element.toString();
	}
	
	// id="boCode-tableKey" ab-basic-permission="boCode.table.tableKey" ...
	public String getSubAttrs(IBusTableRel rel) {
			StringBuilder sb = new StringBuilder();
			sb.append( " id="+"\""+ rel.getBusObj().getKey()+"-"+rel.getTableKey()+"\" ");
			
			//一对多的三层情况下弹框展示
			if(rel.getType().equals(BusTableRelType.ONE_TO_MANY.getKey()) 
					&& !rel.getParent().getType().equals(BusTableRelType.MAIN.getKey())) {
				
				sb.append(" v-transfer-dom ");
				sb.append(" v-if=\"subTempData."+ rel.getTableKey() +"List\" ");
			}
			
			sb.append(" table-key=\""+rel.getTableKey()+"\" ");
			return sb.toString() ;
		}
	
	protected void handlePermission(Element element,IBusinessColumn column) {
		element.attr("v-ab-permission", getPermissionPath(column));
	}
	
	
	protected void handleValidateRules(Element element,IBusinessColumn column) {
		super.handleValidateRules(element, column);
		
		element.attr("v-ab-validate", element.attr("ab-validate"));
		element.removeAttr("ab-validate");
	}


	@Override
	public String getGeneratorName() {
		return "vuxGenerator";
	}
}
