package com.springproject.gab_photography.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String user,String email, String msg) throws MailException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo("gab.mannu@hotmail.it");
        mailMessage.setFrom(email);
        mailMessage.setSubject("My Website");
        mailMessage.setText(msg);

        javaMailSender.send(mailMessage);

    }



}