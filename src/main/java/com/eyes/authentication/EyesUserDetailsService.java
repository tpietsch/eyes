package com.eyes.authentication;

import com.eyes.user.database.models.UserEntity;
import com.eyes.user.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
public class EyesUserDetailsService implements UserDetailsService, Serializable {

    @Autowired
    UserRepository mUserRepository;

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = mUserRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("");
        }

        Set<GrantedAuthority> roles = new HashSet<>();
        if(username.contains("admin")){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_ADMIN");
            roles.add(grantedAuthority);
        }else{
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
            roles.add(grantedAuthority);
        }
        return new EyesUser(user.getEmail(), user.getPassword(), roles, user);
    }

}
