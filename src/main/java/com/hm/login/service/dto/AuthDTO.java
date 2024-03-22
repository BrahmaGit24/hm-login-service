package com.hm.login.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {
	
	private String access_token;
	private String token_type;
	private String refresh_token;
	private Long expires_in;
	private String scope;
	private String jti;
}
