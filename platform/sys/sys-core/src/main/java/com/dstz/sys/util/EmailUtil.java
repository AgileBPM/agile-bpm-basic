package com.dstz.sys.util;

import com.dstz.base.core.util.PropertyUtil;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

/**
 * <pre>
 * 描述：ab邮件工具
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2018年9月13日
 * 版权:summer
 * </pre>
 */
public class EmailUtil {

	private static MailAccount account;

	/**
	 * <pre>
	 * 用ab的邮箱发邮件
	 * </pre>
	 * 
	 * @param email
	 *            目标邮件 eg:aschs@qq.com
	 * @param subject
	 *            主题
	 * @param content
	 *            内容（内容支持html）
	 */
	public static void send(String email, String subject, String content) {
		MailUtil.send(account(), CollUtil.newArrayList(email), subject, content, true);
	}
	
	private static  MailAccount account() {
		if(account != null) return account;
		
		MailAccount mailAccount = new MailAccount();
		String host = PropertyUtil.getProperty("mail.host");
		int port = PropertyUtil.getIntProperty("mail.port");
		boolean isSSL = PropertyUtil.getBoolProperty("mail.ssl");
		String user = PropertyUtil.getProperty("mail.nickName");
		String from = PropertyUtil.getProperty("mail.address");
		String pass = PropertyUtil.getProperty("mail.password");
		mailAccount.setHost(host);
		mailAccount.setPort(port);
		mailAccount.setFrom(from);
		mailAccount.setUser(user);
		mailAccount.setPass(pass);
		mailAccount.setSslEnable(isSSL);
		
		setAccount(mailAccount);
		return mailAccount;
	}
	
	/**
	 * spring boot项目启动的时候设置参数
	 * @param account
	 */
	public static void setAccount(MailAccount account) {
		EmailUtil.account = account;
	}
}
