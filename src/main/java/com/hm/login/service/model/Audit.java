package com.hm.login.service.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {

	private Long createdById;
	private Date createdDate;
	private Long modifiedById;
	private Date modifiedDate;

}
