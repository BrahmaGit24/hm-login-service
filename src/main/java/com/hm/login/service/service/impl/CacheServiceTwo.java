package com.hm.login.service.service.impl;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CacheServiceTwo {

    private LoadingCache<Object, String> otpCache;
    private LoadingCache<Object, Object> passwordLinkCache;

    @Autowired
    public CacheServiceTwo(@Value("${otp-expiry}") Integer EXPIRE_MINS,
                           @Value("${passwordlink-expiry}") Integer EXPIRE_HOURS) {
        log.debug("Initializing CacheService");
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .concurrencyLevel(10) // Adjust based on your application's requirements
                .build(CacheLoader.from(key -> null));

        passwordLinkCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_HOURS, TimeUnit.HOURS)
                .concurrencyLevel(10) // Adjust based on your application's requirements
                .build(CacheLoader.from(key -> null));
    }

    public String generateOTP(String key) {
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        otpCache.put(key, otp);
        log.debug("Generated OTP for key: {}", key);
        return otp;
    }

    public Object generatePasswordLinkSessionId(String key) {
        Instant now = Instant.now();
        passwordLinkCache.put(key, now);
        return now;
    }

    public String getOtp(String key) {
        log.debug("Fetching OTP for key: {}", key);
        return otpCache.getUnchecked(key);
    }

    public Object getPasswordLinkSessionId(String key) {
        return passwordLinkCache.getUnchecked(key);
    }

    public void clearOTP(String key) {
        otpCache.invalidate(key);
    }

    public void clearPasswordLinkSessionId(String key) {
        passwordLinkCache.invalidate(key);
    }

}
