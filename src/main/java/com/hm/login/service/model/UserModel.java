package com.hm.login.service.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name="user",schema="login")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String emailId;

    private String firstName;

    private String lastName;

    private String passwordSalt;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private UserRole userRole;

    @Transient
    private String otp;

    private Long createdById;

    private Date createdDate;

    private Long modifiedById;

    private Date modifiedDate;
}
