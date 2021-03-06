package com.hxh.Quesan.util;


import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;


@Service
public class MailSender implements InitializingBean {
    private static final Logger logger=LoggerFactory.getLogger(MailSender.class);
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    VelocityEngine velocityEngine;

    public boolean sendWithHTMLTemplate(String to, String subject,//给谁，标题
                                        String template, Map<String,Object> model){
        try{
            String nick=MimeUtility.encodeText("朱一龙哈哈哈哈");
            InternetAddress from=new InternetAddress(nick+"<609117168@qq.com>");//发件人
            MimeMessage mimeMessage=javaMailSender.createMimeMessage();//邮件正文
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);//通过helper传入正文啊什么的
            String result=VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,template,"UTF-8",model);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result,true);
            javaMailSender.send(mimeMessage);
            return true;
        }catch(Exception e){
            logger.error("发送邮件失败："+e.getMessage());
            return false;
        }
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setUsername("609117168@qq.com");
        javaMailSender.setPassword("akeobsjcftbubfgc");
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPort(465);
        javaMailSender.setProtocol("smtps");
        javaMailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable",true);
        javaMailSender.setJavaMailProperties(javaMailProperties);
    }
}
