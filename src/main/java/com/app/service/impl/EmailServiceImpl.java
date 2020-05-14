package com.app.service.impl;

import com.app.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Максим Зеленский
 */

@Service
public class EmailServiceImpl implements EmailService {
    private Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);

            log.info("IN sendMessage: email successfully sent");
        } catch (MailException e) {
            log.error("IN sendMessage: " + e.getMessage());
        }
    }

    @Override
    public void sendHtmlMessage(String to, String subject, String htmlMsg) {
        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            message.setContent(htmlMsg, "text/html");

            helper.setTo(to);
            helper.setSubject(subject);

            emailSender.send(message);

            log.info("IN sendHtmlMessage: email successfully sent");
        } catch (MessagingException e) {
            log.error("IN sendHtmlMessage: " + e.getMessage());
        }
    }

    @Override
    public void sendServiceSuccessfullyProvidedMessage(String to) {
        final String subject = "Сезонные услуги";
        final String htmlMsg = "<h3>Ваша заявка на оказание услуги была принята</h3>";
        sendHtmlMessage(to, subject, htmlMsg);
    }

    @Override
    public void sendServiceProvideFailureMessage(String to) {
        final String subject = "Сезонные услуги";
        final String htmlMsg = "<h3>Ваша заявка на оказание услуги была отклонена," +
                " так как оказание данной услуги завершилось</h3>";
        sendHtmlMessage(to, subject, htmlMsg);
    }
}
