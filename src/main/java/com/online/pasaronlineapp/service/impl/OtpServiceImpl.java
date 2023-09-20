package com.online.pasaronlineapp.service.impl;

import com.online.pasaronlineapp.service.OtpService;
import lombok.extern.slf4j.Slf4j;
import com.google.common.cache.*;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class OtpServiceImpl implements OtpService {

    private static final Integer EXPIRE_MINS = 5;
    private LoadingCache cache;

    public OtpServiceImpl() {
        super();
        cache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(
                new CacheLoader<String, Integer>() {

                    @Override
                    public Integer load(String key) throws Exception {
                        return 0;
                    }
                }
        );
    }

    @Override
    public Integer generateOtp(String key) {
        Random random = new Random();
        Integer otp = 100000 + random.nextInt(900000);
        cache.put(key, otp);
        return otp;
    }

    @Override
    public Integer getOtp(String key) {
        try {
            return (Integer) cache.get(key);
        } catch (ExecutionException e) {
            return 0;
        }
    }

    @Override
    public void clearOtp(String key) {
        cache.invalidate(key);
    }
}
