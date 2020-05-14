package com.app.service;

/**
 * @author Максим Зеленский
 */
public interface EmailService {
    void sendMessage(String to, String subject, String text);
    void sendHtmlMessage(String to, String subject, String htmlMsg);
    void sendServiceSuccessfullyProvidedMessage(String to);
    void sendServiceProvideFailureMessage(String to);
}
