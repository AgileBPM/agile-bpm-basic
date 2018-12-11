package com.dstz.sys.simplemq.handler.msg;

import org.springframework.stereotype.Component;

import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.jms.model.msg.NotifyMessage;
import com.dstz.sys.util.ContextUtil;

/**
 * 内部消息处理器。
 *
 * @author jeff
 */
@Component("innerHandler")
public class InnerHandler extends AbsNotifyMessageHandler<NotifyMessage> {

    @Override
    public String getType() {
        return "inner";
    }


    public String getTitle() {
        return "内部消息";
    }


    public boolean getIsDefault() {
        return true;
    }


    public boolean getSupportHtml() {
        return false;
    }

	public boolean handlerMessage() {
		return false;
	}

	@Override
	public boolean sendMessage(NotifyMessage message) {
		  if (1 == 1) return false;
		  
	        IUser sender = ContextUtil.getCurrentUser();


	        return false;
	}

}
