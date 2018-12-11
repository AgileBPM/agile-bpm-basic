package com.dstz.sys.core.model;

import com.dstz.base.core.model.BaseModel;

/**
 * <pre>
 * 描述：系统附件信息
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年6月7日
 * 版权:summer
 * </pre>
 */
public class SysFile extends BaseModel {
	/**
	 * 附件名
	 */
	private String name;
	/**
	 * <pre>
	 * 这附件用的是上传器
	 * 具体类型可以看 IUploader 的实现类
	 * </pre>
	 */
	private String uploader;
	/**
	 * <pre>
	 * 路径，这个路径能从上传器中获取到对应的附件内容
	 * 所以也不一定是路径，根据不同上传器会有不同值
	 * </pre>
	 */
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
