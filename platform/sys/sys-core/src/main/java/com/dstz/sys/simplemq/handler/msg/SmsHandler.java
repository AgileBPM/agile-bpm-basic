package com.dstz.sys.simplemq.handler.msg;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dstz.base.core.util.StringUtil;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.jms.model.msg.NotifyMessage;

import cn.hutool.core.collection.CollectionUtil;

/**
 * 短消息发送处理器 TODO 
 *
 * @author jeff
 */
@Component("smsHandler")
public class SmsHandler extends AbsNotifyMessageHandler<NotifyMessage> {

    @Override
    public String getType() {
        return "sms";
    }

    @Override
    public boolean sendMessage(NotifyMessage message) {
    	LOGGER.warn("短信大于消息发送尚未实现："+ JSON.toJSONString(message));
        // 调用阿里大于
        List<IUser> recievers =null; message.getReceivers();
        String content = message.getTextContent();
        String templateCode = null;//message.getSmsTemplateNo();

        if (StringUtil.isEmpty(content) || CollectionUtil.isEmpty(recievers)) return false;


        for (IUser user : recievers) {
            if (StringUtil.isEmpty(user.getMobile())) continue;
			
	/*		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend(alidayuSetting.getExtend());
			req.setSmsType("normal");
			req.setSmsFreeSignName(alidayuSetting.getFreeSignName());
			
			String parmString =TaoBaoUtil.buildParams(vo);
			req.setSmsParamString(parmString);
			req.setRecNum(user.getMobile());
			req.setSmsTemplateCode(templateCode);
			try {
				AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
			} catch (ApiException e) {
				e.printStackTrace();
			}*/
        }
        return true;
    }

    @Override
    public String getTitle() {
        return "短信";
    }

    @Override
    public boolean getIsDefault() {
        return false;
    }

    @Override
    public boolean getSupportHtml() {
        return true;
    }

}
