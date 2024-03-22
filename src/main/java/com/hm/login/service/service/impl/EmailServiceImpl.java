package com.hm.login.service.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("emailService")
public class EmailServiceImpl {

    private JavaMailSender javaMailSender;

    public void sendOtpMessage(String to, String subject, String message) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(message, true);

        javaMailSender.send(msg);
    }
}
