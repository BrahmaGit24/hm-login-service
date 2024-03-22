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
public class CacheService {

    private LoadingCache<Object, String> otpCache;
    private LoadingCache<Object, Object> passwordLinkCache;

    @Autowired
    public CacheService(@Value("${otp-expiry}") Integer EXPIRE_MINS,
                        @Value("${passwordlink-expiry}") Integer EXPIRE_HOURS) {
        log.debug("initial  CacheService");
        otpCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
                .build(new CacheLoader<Object, String>() {
                    @Override
                    public String load(Object key) throws Exception {
                        return "0";
                    }
                });
        passwordLinkCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_HOURS, TimeUnit.HOURS)
                .build(new CacheLoader<Object, Object>() {
                    @Override
                    public Object load(Object key) throws Exception {
                        return "0";
                    }
                });
    }

    public String generateOTP(String key) {
        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        otpCache.put(key, otp);
        log.debug("generate OTP ");
        return otp;
    }

    public Object generatePasswordLinkSessionId(String key) {
        Instant now = Instant.now();
        passwordLinkCache.put(key, now);
        return now;
    }

    public String getOtp(String key) {
        try {
            log.debug("getOtp "+key);
            return otpCache.get(key);
        } catch (Exception e) {
            log.error("error in getOtp ");
            return "0";
        }
    }

    public Object getPasswordLinkSessionId(String key) {
        try {
            return passwordLinkCache.get(key);
        } catch (Exception e) {
            return "0";
        }
    }

    public void clearOTP(String key) {
        otpCache.invalidate(key);
    }

    public void clearPasswordLinkSessionId(String key) {
        passwordLinkCache.invalidate(key);
    }
}
