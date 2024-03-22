package com.hm.login.service.dto;

import com.hm.login.service.model.Audit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO extends Audit {
	
	private Long userId;
	
	private String emailId;
	
	private String firstName;
	
	private String lastName;
	
	private String passwordSalt;
	
	private Long roleId;
	
}
