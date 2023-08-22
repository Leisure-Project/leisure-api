package com.leisure.service;

public interface EmailService {
    void sendEmail(Long clientId, String clientEmail) throws Exception;
    String getEmailTemplate(Long clientId) throws Exception;
}
