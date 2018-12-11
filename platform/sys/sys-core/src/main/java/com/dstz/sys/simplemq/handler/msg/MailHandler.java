package com.dstz.sys.simplemq.handler.msg;

import com.alibaba.fastjson.JSON;
import com.dstz.base.core.util.StringUtil;
import com.dstz.org.api.model.IUser;
import com.dstz.sys.api.jms.model.msg.NotifyMessage;
import com.dstz.sys.api.service.SysIdentityConvert;
import com.dstz.sys.util.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 邮件消息处理器。
 *
 * @author jeff
 */
@Component("mailHandler")
public class MailHandler extends AbsNotifyMessageHandler<NotifyMessage> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailHandler.class);

    @Resource
    SysIdentityConvert sysIdentityConvert;

    @Override
    public String getType() {
        return "email";
    }


    @Override
    public String getTitle() {
        return "邮件";
    }


    @Override
    public boolean getSupportHtml() {
        return true;
    }

    @Override
    public boolean sendMessage(NotifyMessage notifMessage) {
        List<IUser> recievers = sysIdentityConvert.convert2Users(notifMessage.getReceivers());
        for (IUser reciver : recievers) {
            String email = reciver.getEmail();
            if (StringUtil.isEmpty(email)) {
                continue;
            }
            try {
                EmailUtil.send(email, notifMessage.getSubject(), notifMessage.getHtmlContent());
            } catch (Exception e) {
                LOGGER.error("发送邮件失败!, 发送参数：{}", JSON.toJSONString(notifMessage), e);
            }
        }
        LOGGER.debug("发送邮件成功 ：{}", JSON.toJSONString(notifMessage));
        return true;
    }

}
