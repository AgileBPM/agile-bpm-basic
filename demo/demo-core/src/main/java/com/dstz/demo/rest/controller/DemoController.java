package com.dstz.demo.rest.controller;

import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dstz.base.rest.BaseController;

import com.dstz.base.api.aop.annotion.CatchErr;

import com.dstz.demo.core.manager.DemoManager;
import com.dstz.demo.core.model.Demo;


/**
 * 案例 控制器类<br/>
 * @author  aschs
 * </pre>
 */
@RestController
@RequestMapping("/demo/demo")
public class DemoController extends BaseController<Demo>{
	@Resource
	DemoManager demoManager;
	
	
	@Override
	protected String getModelDesc() {
		return "案例";
	}
	   
}
