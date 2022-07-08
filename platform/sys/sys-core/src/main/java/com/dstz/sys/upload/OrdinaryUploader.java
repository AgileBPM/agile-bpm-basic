package com.dstz.sys.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.util.PropertyUtil;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;

/**
 * <pre>
 * 描述：普通的上传器
 * 上传到服务器的某个文件夹中
 * 每次上传时会自动放在当前日期yyyyMMdd的目录下
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年6月1日
 * 版权:summer
 * </pre>
 */
@Service
public class OrdinaryUploader extends AbstractUploader {

	@Override
	public String type() {
		return "ordinary";
	}

	@Override
	public String upload(InputStream is, String name) {
		FileUtil.writeFromStream(is, getPath(name));
		return getPath(name);
	}

	@Override
	public InputStream take(String path) {
		try {
			return new FileInputStream(new File(path));
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public void remove(String path) {
		FileUtil.del(path);
	}

	private String getPath(String name) {
		return PropertyUtil.getProperty("uploader.ordinary.path") +  DateUtil.format(new Date(), "yyyyMMdd") + File.separator + name;
	}

}
