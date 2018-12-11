package com.dstz.sys.upload;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.core.id.IdUtil;

/**
 * <pre>
 * 描述：数据库上传器
 * 把流转成bytes放到数据库中
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年6月8日
 * 版权:summer
 * </pre>
 */
@Service
public class DbUploader extends AbstractUploader {
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public String type() {
		return "db";
	}

	@Override
	public String upload(InputStream is, String name) {
		try {
			String id = IdUtil.getSuid();
			jdbcTemplate.update("INSERT INTO db_uploader VALUES (?,?)", id, IOUtils.toByteArray(is));
			return id;
		} catch (IOException e) {
			throw new BusinessException(e);
		}
	}

	@Override
	public InputStream take(String path) {
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from db_uploader where id_ = ?", path);
		byte[] bytes = (byte[]) mapList.get(0).get("bytes_");
		return new ByteArrayInputStream(bytes);
	}

	@Override
	public void remove(String path) {
		jdbcTemplate.update("delete from db_uploader where id_ = ?", path);
	}

}
