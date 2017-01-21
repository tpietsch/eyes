package com.eyes.authentication;

import com.eyes.authentication.database.models.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

public class EyesUser extends User implements Serializable {
    UserEntity userEntity;

    public EyesUser(String username, String password, Collection<? extends GrantedAuthority> authorities, UserEntity user) {
        super(username, password, authorities);
        this.userEntity = user;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

}
