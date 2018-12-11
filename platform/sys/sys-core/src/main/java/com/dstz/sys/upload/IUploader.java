package com.dstz.sys.upload;

import java.io.InputStream;

/**
 * <pre>
 * 描述：系统的上传流接口
 * ps：跟任何业务都无关，可作为工具类提供出来
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年6月1日
 * 版权:summer
 * </pre>
 */
public interface IUploader {
	/**
	 * <pre>
	 * 类型
	 * </pre>
	 * 
	 * @return
	 */
	String type();

	/**
	 * <pre>
	 * 上传流
	 * </pre>
	 * 
	 * @param is
	 *            流
	 * @param name
	 *            流名
	 * @return 流路径，take的时候以这个路径能获取到对应的流
	 */
	String upload(InputStream is, String name);

	/**
	 * <pre>
	 * 根据流路径 获取流
	 * </pre>
	 * 
	 * @param path
	 * @return
	 */
	InputStream take(String path);
	
	/**
	 * <pre>
	 * 删除流
	 * </pre>
	 * 
	 * @param path
	 */
	void remove(String path);
}
