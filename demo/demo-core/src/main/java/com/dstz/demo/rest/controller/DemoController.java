package com.dstz.demo.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.id.IdUtil;
import com.dstz.base.core.util.RestTemplateUtil;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.rest.BaseController;
import com.dstz.bpm.api.model.remote.BpmRemoteBusinessData;
import com.dstz.bpm.api.model.remote.FormHandlerResult;
import com.dstz.bus.api.remote.RemoteBusinessData;
import com.dstz.demo.core.manager.DemoManager;
import com.dstz.demo.core.model.Demo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 案例 控制器类<br/>
 * 
 * @author aschs
 *    
 */
@RestController
@RequestMapping("/demo/demo/")
@Api(description = "DEMO 案例模块")
public class DemoController extends BaseController<Demo> {
	@Resource
	DemoManager demoManager;

	@Override
	protected String getModelDesc() {
		return "案例";
	}
	
	 
	 
	@RequestMapping("testRemote")
	@CatchErr(write2response = true, value = "testRemote失败")
	public void testRemote(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = "http://localhost:8080/agile-bpm-platform/demo/demo/saveData";
		RemoteBusinessData<JSONObject> remoteBusinessData = new RemoteBusinessData<>();
		ResultMsg<JSONObject> resp = RestTemplateUtil.post(url, remoteBusinessData, ResultMsg.class);
		System.out.println(resp);
	}

	
	
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/agile-bpm-platform/bus/businessTable/listJson";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Cookie", "JSESSIONID=5AB79F7C31EE04FEB34EEFADA8B1C726;JSESSIONID=A9E81C4FC673DABE48F53D6E722DD758");
		HttpEntity<String> entity = new HttpEntity<>("abc", headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
		System.out.println(responseEntity.getBody());
	}
}
