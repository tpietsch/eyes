package com.eyes.authentication;

import com.eyes.user.database.models.UserEntity;
import com.eyes.user.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserUtil {
    @Autowired
    UserRepository mUserRepository;

    public UserEntity getAuthenticatedUserEntity() {
        try {
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String) {
               return null;
            }
            String username = ((EyesUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            return mUserRepository.findByEmail(username);
        } catch (Exception e) {
            return null;
        }
    }
}
