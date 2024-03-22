package com.hm.login.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String userName;
	private String password;

	private Collection<GrantedAuthority> authorittyList = new ArrayList<GrantedAuthority>();
}
