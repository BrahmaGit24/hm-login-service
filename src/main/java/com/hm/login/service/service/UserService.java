package com.hm.login.service.service;

import com.hm.login.service.dto.Result;
import com.hm.login.service.dto.UserDTO;
import com.hm.login.service.dto.UserDetailsDTO;
import com.hm.login.service.dto.UserSearch;

public interface UserService {

    Result saveUserModel(UserDetailsDTO details);

    Result searchUser(UserSearch search);
}
