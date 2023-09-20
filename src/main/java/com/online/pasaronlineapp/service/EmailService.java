package com.online.pasaronlineapp.service;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {

    void sendOtpMessage(String to, String subject, String message) throws MessagingException;
}
