package security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import security.util.JwtParseUtil;

@Component
public class JwtAuthentication implements AuthenticationProvider {

    @Autowired
    @Qualifier("userServiceTwo")
    UserDetailsService userServiceTwo;

    @Autowired
    JwtParseUtil jwtParseUtil;

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        authentication.setAuthenticated(true);
        return authentication;
    }

}
