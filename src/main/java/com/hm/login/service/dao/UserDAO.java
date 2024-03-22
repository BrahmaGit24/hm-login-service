package com.hm.login.service.dao;

import com.hm.login.service.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserModel, Long> {

	UserModel findByEmailId(String userId);

	UserModel findByEmailIdIgnoreCase(String userId);

	UserModel findByUserId(Long userId);
}
