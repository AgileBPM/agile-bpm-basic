package com.dstz.sys.api.jms.model.msg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.model.SysIdentity;
/**
 * 通知消息的DTO
 * @author Jeff
 *
 */
public class NotifyMessage implements Serializable{
    private String subject;
    private String htmlContent;
    private String textContent;
    private IUser sender;
    private List<SysIdentity> receivers;


    public NotifyMessage(String subject, String htmlContent, IUser sender, List<SysIdentity> receivers) {
        this.subject = subject;
        this.sender = sender;
        this.receivers = receivers;
        this.htmlContent = htmlContent;
    }

    private Map<String, Object> extendVars = new HashMap<String, Object>();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    
    public IUser getSender() {
        return sender;
    }

    public void setSender(IUser sender) {
        this.sender = sender;
    }

    public List<SysIdentity> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<SysIdentity> receivers) {
        this.receivers = receivers;
    }

    public Map<String, Object> getExtendVars() {
        return extendVars;
    }

    public void setExtendVars(Map<String, Object> extendVars) {
        this.extendVars = extendVars;
    }
	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}


}
