package com.dstz.sys.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.rest.BaseController;
import com.dstz.sys.core.model.LogErr;

@RestController
@RequestMapping("/sys/sysLogErr/")
public class SysLogErrController extends BaseController<LogErr> {

	@Override
	protected String getModelDesc() {
		return "系统异常日志";
	}

}
