package com.example.sondage.Service.serviceUser;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void send (String to, String subject, String body) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(message, true); // true indicates
        helper.setSubject(subject);
        helper.setFrom("aya.mrade@gmaol.com");

        helper.setTo(to);
        helper.setText(body, true); // true indicates html
        // continue using helper object for more functionalities like adding attachments, etc.

        javaMailSender.send(message);
    }

    public void sendSetPassword(String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("set password ");
        mimeMessageHelper.setText("""
        <div>
          <a href="http://localhost:8000/COEXIST/api/set-password">click link to verify</a>
        </div>
        """.formatted(email), true);

        javaMailSender.send(mimeMessage);
    }

}
