package com.badboyz.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * Created by yusuf on 4/26/2016.
 */
@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOGGER = LogManager.getLogger(EmailService.class);

    public boolean sendEmail(String email, String sender, String subject, String body) {

        boolean returnVal = false;
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            message.setTo(email);
            message.setFrom(sender);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(mimeMessage);
            returnVal = true;

        } catch (Exception e) {
            LOGGER.catching(e);
        }

        return returnVal;
    }
}

