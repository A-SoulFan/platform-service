package com.asoulfan.asfbbs.user.helper;

import static com.asoulfan.asfbbs.user.common.NacosConstant.DEFAULT_NACOS_GROUP;


import org.springframework.stereotype.Component;

import com.asoulfan.common.nacos.NacosValue;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;

/**
 * @author asuka
 * @date 2021/11/06
 */
@Component
public class MailHelper {
    @NacosValue(dataId = "mailAccount", group = DEFAULT_NACOS_GROUP)
    private MailAccount mailAccount;

    public String send(String to, String subject, String content, boolean isHtml) {
        return MailUtil.send(mailAccount, to, subject, content, false);
    }
}
