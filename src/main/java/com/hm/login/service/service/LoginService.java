package com.hm.login.service.service;

import com.hm.login.service.dto.JwtUserRequest;
import com.hm.login.service.service.impl.JwtUserResponse;

public interface LoginService {
    JwtUserResponse login(JwtUserRequest request);

    JwtUserResponse verifyOTP(JwtUserRequest request);
}
