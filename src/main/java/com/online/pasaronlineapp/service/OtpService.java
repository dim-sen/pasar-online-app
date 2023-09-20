package com.online.pasaronlineapp.service;

import org.springframework.stereotype.Service;

@Service
public interface OtpService {

    Integer generateOtp(String key);

    Integer getOtp(String key);

    void clearOtp(String key);
}
