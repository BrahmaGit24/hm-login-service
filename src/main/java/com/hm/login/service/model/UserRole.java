package com.hm.login.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_roles", schema = "login")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "user" })
public class UserRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userRoleId;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = true)
	private UserModel user;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = true)
	private Roles role;

	private Date roleStartDate;

	private Date roleEndDate;

	private boolean isActive;

	private Long createdById;

	private Date createdDate;

	private Long modifiedById;

	private Date modifiedDate;
}
