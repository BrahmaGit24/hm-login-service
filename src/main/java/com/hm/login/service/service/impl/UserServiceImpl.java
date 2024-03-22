package com.hm.login.service.service.impl;

import com.hm.login.service.dao.UserDAO;
import com.hm.login.service.dto.Result;
import com.hm.login.service.dto.UserDTO;
import com.hm.login.service.dto.UserDetailsDTO;
import com.hm.login.service.dto.UserSearch;
import com.hm.login.service.exception.CustomException;
import com.hm.login.service.model.Roles;
import com.hm.login.service.model.UserModel;
import com.hm.login.service.model.UserRole;
import com.hm.login.service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    /** The b crypt password encoder. */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Result saveUserModel(UserDetailsDTO details) {
        Result result = null;
        try {

            UserModel emailExistUser = userDAO.findByEmailIdIgnoreCase(details.getEmailId());
            if (emailExistUser != null) {
                result = new Result();
                result.setStatusCode(HttpStatus.BAD_REQUEST.value());
                result.setSuccessMessage(details.getEmailId() + "Email already exist");
            } else {
                UserModel user = new UserModel();
                user.setEmailId(details.getEmailId());
                user.setFirstName(details.getFirstName());
                user.setLastName(details.getLastName());
                String encodedPassword = bCryptPasswordEncoder.encode(details.getPasswordSalt());
//				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//			    String encodedPassword = encoder.encode(details.getPasswordSalt());
                user.setPasswordSalt(encodedPassword);
                user.setCreatedById(details.getCreatedById());
                user.setModifiedById(details.getModifiedById());
                user.setCreatedDate(new Date());
                user.setModifiedDate(new Date());

                UserRole userRole = new UserRole();
                Roles roles = new Roles();
                roles.setRoleId(details.getRoleId());
                userRole.setRole(roles);
                userRole.setCreatedById(details.getCreatedById());
                userRole.setModifiedById(details.getModifiedById());
                userRole.setCreatedDate(new Date());
                userRole.setModifiedDate(new Date());
                userRole.setActive(true);
                userRole.setRoleStartDate(new Date());
                userRole.setRoleEndDate(null);
                userRole.setUser(user);

                user.setUserRole(userRole);

                UserModel serverEmployee = userDAO.save(user);

                result = new Result(serverEmployee);
                result.setStatusCode(HttpStatus.OK.value());
                result.setSuccessMessage("User Saved SuccessFully.");
            }
        } catch (Exception e){
            log.error("Error in save User :: " + e.getMessage());
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @Override
    public Result searchUser(UserSearch search) {
        Result result = null;
        try {
            UserModel user = userDAO.findByUserId(search.getUserId());

            if (user == null) {
                result = new Result();
                result.setStatusCode(HttpStatus.NOT_FOUND.value());
                result.setErrorMessage("Not Found.");
            } else {
                result = new Result(user);
                result.setStatusCode(HttpStatus.OK.value());
                result.setSuccessMessage("Getting Succussfully.");
            }
        } catch (Exception e) {
            log.error("Error in serach ::" + e.getMessage());
        }
        return result;
    }
}
