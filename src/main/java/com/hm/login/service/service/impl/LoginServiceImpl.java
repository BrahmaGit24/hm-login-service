package com.hm.login.service.service.impl;

import com.hm.login.service.dao.UserDAO;
import com.hm.login.service.dto.AuthDTO;
import com.hm.login.service.dto.JwtUserRequest;
import com.hm.login.service.exception.CustomException;
import com.hm.login.service.model.UserModel;
import com.hm.login.service.service.LoginService;
import com.hm.login.service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** The otp service. */
    @Autowired
    public CacheServiceTwo cacheService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public JwtUserResponse login(JwtUserRequest request) {
        JwtUserResponse jwtUserResponse = null;
        try {
            jwtUserResponse = new JwtUserResponse();
            UserModel userModel = userDAO.findByEmailIdIgnoreCase(request.getEmailId().toLowerCase());

            if (userModel == null) {
                jwtUserResponse.setErrorMessage("Invalid Email Address or Not Registered.");
                jwtUserResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            } else {
                if (!passwordEncoder.matches(request.getPassword(), userModel.getPasswordSalt())) {
                    jwtUserResponse.setErrorMessage("Invalid PassWord.");
                    jwtUserResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
                } else {
                    jwtUserResponse.setUser(userModel);
                    String otp = cacheService.generateOTP(request.getEmailId().toLowerCase().trim());
                    jwtUserResponse.getUser().setOtp(otp);
                    jwtUserResponse.setSuccessMessage("OTP has been send to Mail");
                    jwtUserResponse.setStatusCode(HttpStatus.OK.value());
                }
            }
        } catch (Exception e) {
            throw new CustomException("Error in Login :: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return jwtUserResponse;
    }


    @Override
    public JwtUserResponse verifyOTP(JwtUserRequest request) {
        JwtUserResponse jwtUserResponse = null;
        try {
            String emailId = request.getEmailId().toLowerCase().trim();
            String serverOtp = cacheService.getOtp(emailId);
            if (request.getOtp().equals(serverOtp)) {
                UserModel user = userDAO.findByEmailIdIgnoreCase(emailId);
                jwtUserResponse = new JwtUserResponse(user,null,null);
                getJwtToken(jwtUserResponse,emailId,request.getPassword());
                jwtUserResponse.setStatusCode(HttpStatus.OK.value());
                jwtUserResponse.setSuccessMessage("OTP is successfully validated");
                cacheService.clearOTP(emailId);
            } else if (Integer.valueOf(serverOtp) == 0) {
                throw new CustomException("OTP is expired", HttpStatus.NOT_FOUND);
            } else {
                throw new CustomException("OTP is invalid", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new CustomException("Error in verifyOTP :: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return jwtUserResponse;
    }

    /**
     * Gets the jwt token.
     *
     * @param jwtUserResponse the jwt user response
     * @param password        the password
     * @return the jwt token
     */
    private void getJwtToken(JwtUserResponse jwtUserResponse,String emailId, String password) {
        try {
            final AuthDTO jwt = jwtUtil.generateToken(jwtUserResponse, password);
            jwtUserResponse.setAccessToken(jwt.getAccess_token());
            jwtUserResponse.setRefreshToken(jwt.getRefresh_token());
//			jwtUserResponse.getUser().setLastLogInDate(new Date());
//			userDAO.save(jwtUserResponse.getUser());
//			String accessToken = "Bearer " + jwt.getAccess_token();
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
