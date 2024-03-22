package com.hm.login.service.service.impl;


import com.hm.login.service.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtUserResponse implements UserDetails {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private UserModel user;
    private String accessToken;
    private String refreshToken;
    private int statusCode;
    private String successMessage;
    private String errorMessage;
    private Boolean otpEnable;

    public JwtUserResponse(UserModel user, String accessToken, String refreshToken) {
        this.user = user;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(this.user.getUserRole().getRole().getRoleName()));
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPasswordSalt();
    }

    @Override
    public String getUsername() {
        return user.getEmailId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
