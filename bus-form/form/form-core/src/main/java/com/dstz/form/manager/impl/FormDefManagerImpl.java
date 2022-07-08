package com.dstz.form.manager.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.query.QueryFilter;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.PropertyUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.manager.impl.BaseManager;
import com.dstz.bus.api.model.IBusTableRel;
import com.dstz.bus.api.model.IBusinessObject;
import com.dstz.bus.api.service.IBusinessObjectService;
import com.dstz.bus.api.service.IBusinessTableService;
import com.dstz.form.api.model.FormType;
import com.dstz.form.dao.FormDefDao;
import com.dstz.form.generator.AbsFormElementGenerator;
import com.dstz.form.manager.FormDefManager;
import com.dstz.form.manager.FormTemplateManager;
import com.dstz.form.model.FormDef;
import com.dstz.form.model.FormTemplate;
import com.dstz.form.model.design.FormGroup;
import com.dstz.form.model.design.FormOverallArrangement;
import com.dstz.form.model.design.FormTab;
import com.dstz.sys.api.freemark.IFreemarkerEngine;
import com.dstz.sys.api.model.ISysTreeNode;
import com.dstz.sys.api.service.ISysTreeNodeService;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;

/**
 * 表单 Manager处理实现类
 *
 * @author aschs
 * @email aschs@qq.com
 * @time 2018-03-19 20:30:46
 */
@Service("formDefManager")
public class FormDefManagerImpl extends BaseManager<String, FormDef> implements FormDefManager {
	protected  Logger LOG = LoggerFactory.getLogger(getClass());
	@Resource
	FormDefDao formDefDao;
	@Autowired
	ISysTreeNodeService sysTreeNodeService;
	@Autowired
	IBusinessObjectService businessObjectService;
	@Autowired
	IBusinessTableService businessTableService;
	@Autowired
	FormTemplateManager formTemplateManager;
	@Autowired
    IFreemarkerEngine freemarkEngine;

	@Override
	public FormDef getByKey(String key) {
		FormDef form = formDefDao.getByKey(key);
		//return Assert.notNull(form, "业务表单[" + key + "]不存在，请检查");
		return form;
	}

	@Override
	public void saveBackupHtml(FormDef formDef) {
		String formDefPath = PropertyUtil.getFormDefBackupPath();
		if (StringUtil.isEmpty(formDefPath)) {
			return;
		}

		ISysTreeNode node = sysTreeNodeService.getById(formDef.getGroupId());
		String fileName = formDefPath + File.separator + node.getKey() + File.separator + formDef.getKey() + ".html";
		FileUtil.writeUtf8String(formDef.getHtml(), fileName);
	}

	@Override
	public String getBackupHtml(FormDef formDef) {
		String formDefPath = PropertyUtil.getFormDefBackupPath();
		if (StringUtil.isNotEmpty(formDefPath)) {
			ISysTreeNode node = sysTreeNodeService.getById(formDef.getGroupId());
			String fileName = formDefPath + File.separator + node.getKey() + File.separator + formDef.getKey() + ".html";
			formDef.setHtml(FileUtil.readUtf8String(fileName));
		}

		return formDef.getHtml();
	}

	
	@Override
	public String generateFormHtml(String boKey, JSONArray templateConfig,String fromType) {
		String boDesign = businessObjectService.getBoOverallArrangement(boKey);
		// 配置了表单设计的统一使用表单设计来生成
		if(StringUtil.isNotEmpty(boDesign)){
			//目前仅仅iview 和vue支持 布局设计
			if(FormType.PC_IVIEW.value().equals(fromType)) {
				return generateIviewOverallArrangementFormHtml(boDesign,boKey,templateConfig);
			}else {
				return generateVueOverallArrangementFormHtml(boDesign,boKey,templateConfig);
			}
		}
		
		IBusinessObject businessObject = businessObjectService.getFilledByKey(boKey);
		
		StringBuilder sb = new StringBuilder();
		for (Object object : templateConfig) {
			JSONObject jsonObject = (JSONObject) object;
			String templateKey = jsonObject.getString("templateKey");
			IBusTableRel relation = businessObject.getRelation().find(jsonObject.getString("tableKey"));
			FormTemplate template = formTemplateManager.getByKey(templateKey);
			if (template == null) {
				LOG.warn("表单模板{}不存在！",templateKey);
				continue;
			}
			Map<String, Object> map = new HashMap<>();
			map.put("relation", relation);
			
			//将所有表单生成器的实现类注入到模板引擎中
			for(AbsFormElementGenerator generator : AppUtil.getImplInstanceArray(AbsFormElementGenerator.class)) {
				map.put(generator.getGeneratorName(), generator);
			}
			
			String html = freemarkEngine.parseByString(template.getHtml(), map);
			
			sb.append(html);
		}
		if(sb.length()>0){
			sb.insert(0,"<div class=\"ivu-form ivu-form-label-right\">");
			sb.append("</div>");
		}

		return sb.toString();
	}
	/**
	 * 业务对象布局后的表单
	 *  <Tabs type="card">
	        <TabPane label="标签一">标签一的内容</TabPane>
	        <TabPane label="标签二">标签二的内容</TabPane>
	        <TabPane label="标签三">标签三的内容</TabPane>
	    </Tabs>
	 * 
	 * @param boDesign
	 * @param templateConfig 
	 * @param boKey 
	 * @return
	 */
	private String generateIviewOverallArrangementFormHtml(String boDesign, String boKey, JSONArray templateConfig) {
		FormOverallArrangement formDesign = JSON.toJavaObject(JSON.parseObject(boDesign), FormOverallArrangement.class);
		IBusinessObject businessObject = businessObjectService.getFilledByKey(boKey);

		
		StringBuffer html = new StringBuffer();
		if(CollectionUtil.isNotEmpty(formDesign.getTabList())) {
			html.append("<Tabs>");
			formDesign.getTabList().forEach(tab ->{
				html.append(" <tab-pane label=\"")
					.append(tab.getComment())
					.append("\">");
				
				html.append(generateGroupsHtml(tab.getGroupList(),businessObject,templateConfig));
				
				html.append("</tab-pane> ");
				
			});
			
			html.append("</Tabs>");
		}else {
			html.append(generateGroupsHtml(formDesign.getGroupList(),businessObject,templateConfig));
		}
		
		if(html.length()>0){
			html.insert(0,
					"<div class=\"ivu-form ivu-form-label-right\">"+
					"<script>\n" + 
					"    window.custFormComponentMixin ={\n" + 
					"        data: function () {\n" + 
					"            return {\"test\":\"helloWorld\"};\n" + 
					"        },\n" + 
					"        created:function(){\n" + 
					"            console.log(\"脚本将会混入自定义表单组件中...\");\n" + 
					"        },methods:{\n" + 
					"            testaaa:function(){alert(1)}\n" + 
					"        }\n" + 
					"    }\n" + 
					"</script>" );
			html.append("</div>");
		}
		
		return html.toString();
	}
	
	/**
	 * PC_VUE 业务对象布局后的表单
		<ul class="nav nav-tabs" role="tablist">
	    	<li class="active"><a href="#home" >Home</a></li>
	    	<li <a href="#profile">Profile</a></li>
	    	<li <a href="#settings">Settings</a></li>
	  	</ul>
	
	  <!-- Tab panes -->
	  <div class="tab-content">
	    <div class="tab-pane active" id="home">...</div>
	    <div class="tab-pane" id="profile">...</div>
	    <div class="tab-pane" id="settings">...</div>
	  </div>
	 * 
	 * @param boDesign
	 * @param templateConfig 
	 * @param boKey 
	 * @return
	 */
	private String generateVueOverallArrangementFormHtml(String boDesign, String boKey, JSONArray templateConfig) {
		FormOverallArrangement formDesign = JSON.toJavaObject(JSON.parseObject(boDesign), FormOverallArrangement.class);
		IBusinessObject businessObject = businessObjectService.getFilledByKey(boKey);

		
		StringBuffer html = new StringBuffer();
		if(CollectionUtil.isNotEmpty(formDesign.getTabList())) {
			html.append("<ul class=\"nav nav-tabs\">");
			 boolean isFirst = true;
			for(FormTab tab : formDesign.getTabList()){
				html.append(
						String.format("<li %s ><a href=\"#%s\"  data-toggle=\"tab\" >%s</a></li>",
								(isFirst? "class=\"active\"":""),tab.getComment(),tab.getComment())
						);
				isFirst = false;
				
			};
			html.append("</ul> ");
			
			isFirst = true;
			html.append("<div class=\"tab-content\">");
			for(FormTab tab : formDesign.getTabList()){
				html.append("<div class=\"tab-pane ").append(isFirst?"active":"").append("\" id=\"").append(tab.getComment()).append("\">")
											.append(generateGroupsHtml(tab.getGroupList(),businessObject,templateConfig))
											.append("</div>");
				isFirst = false;
			}
			html.append("</div>");
		}else {
			html.append(generateGroupsHtml(formDesign.getGroupList(),businessObject,templateConfig));
		}
		
		if(html.length()>0){
			html.insert(0,
					"<div class=\"ivu-form ivu-form-label-right\">"+
					"<script>\n" + 
					"    window.custFormComponentMixin ={\n" + 
					"        data: function () {\n" + 
					"            return {\"test\":\"helloWorld\"};\n" + 
					"        },\n" + 
					"        created:function(){\n" + 
					"            console.log(\"脚本将会混入自定义表单组件中...\");\n" + 
					"        },methods:{\n" + 
					"            testaaa:function(){alert(1)}\n" + 
					"        }\n" + 
					"    }\n" + 
					"</script>" );
			
			html.append("</div>");
		}
		
		return html.toString();
	}
		
	private String generateGroupsHtml(List<FormGroup> groupList, IBusinessObject businessObject, JSONArray templateConfig) {
		StringBuilder groupHtml = new StringBuilder();
		groupList.forEach(group -> {
			String tableKey = group.getKey();
			String templateKey = getTemplateKey(templateConfig,tableKey);
			IBusTableRel relation = businessObject.getRelation().find(tableKey);
			FormTemplate template = formTemplateManager.getByKey(templateKey);
			
			
			Map<String, Object> map = new HashMap<>();
			map.put("relation", relation);
			group.setTableRelation(relation);
			//根据分组对象来生成表单
			map.put("group", group);
			
			//将所有表单生成器的实现类注入到模板引擎中
			for(AbsFormElementGenerator generator : AppUtil.getImplInstanceArray(AbsFormElementGenerator.class)) {
				map.put(generator.getGeneratorName(), generator);
			}
			LOG.debug("templateKey:{},tableKey:{}",templateKey,tableKey);
			String html = freemarkEngine.parseByString(template.getHtml(), map);
			
			groupHtml.append(html);
		});
		
		return groupHtml.toString();
	}
	
	
	// 获取对应模块选择的模板
	private String getTemplateKey(JSONArray templateConfig, String tableKey) {
		for (Object object : templateConfig) {
			JSONObject jsonObject = (JSONObject) object;
			if(jsonObject.getString("tableKey").equals(tableKey)) {
				return jsonObject.getString("templateKey");
			}
		}
		
		throw new BusinessException("tableKey :"+tableKey+"尚未选择模板，请选择模板后生成表单");
	}
	
	@Override
	public List<Map> queryWithBo(QueryFilter queryFilter){
		return formDefDao.queryWithBo(queryFilter);
	}
}
