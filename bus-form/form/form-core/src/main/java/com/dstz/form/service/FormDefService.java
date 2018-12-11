package com.dstz.form.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dstz.form.api.model.IFormDef;
import com.dstz.form.api.service.IFormDefService;
import com.dstz.form.manager.FormDefManager;

@Service
public class FormDefService implements IFormDefService {
	@Autowired
	FormDefManager formDefManager;

	@Override
	public IFormDef getByKey(String key) {
		return formDefManager.getByKey(key);
	}
}
