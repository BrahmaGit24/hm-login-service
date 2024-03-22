package com.hm.login.service.util;

import com.hm.login.service.dto.UserDTO;
import org.springframework.security.core.userdetails.User;


public class UserDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDetails(UserDTO user) {
		super(user.getUserName(), user.getPassword(), user.getAuthorittyList());
	}

}
