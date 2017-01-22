package com.eyes.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private String token;

    public AuthenticationToken(String token, UserDetails userEntity) {
        super(userEntity.getAuthorities());
        this.setDetails(userEntity);
        this.token = token;
    }


    @Override
    public Object getCredentials() {
        return "N/A";
    }

    @Override
    public Object getPrincipal() {
        return getDetails() != null ? (UserDetails) getDetails() : null;
    }

    public String getToken() {
        return token;
    }

}