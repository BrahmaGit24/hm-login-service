package com.hm.login.service.controller;

import com.hm.login.service.dto.JwtUserRequest;
import com.hm.login.service.service.LoginService;
import com.hm.login.service.service.impl.JwtUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/authenticate")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtUserRequest request) {
        JwtUserResponse response = loginService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody JwtUserRequest request) {
        JwtUserResponse response = loginService.verifyOTP(request);
        return ResponseEntity.ok(response);
    }
}
