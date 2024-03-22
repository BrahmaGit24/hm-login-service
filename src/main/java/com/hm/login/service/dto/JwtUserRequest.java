package com.hm.login.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserRequest {
	private String emailId;
	private String password;
	private String otp;
}
