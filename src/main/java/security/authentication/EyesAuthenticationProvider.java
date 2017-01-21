package security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class EyesAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("userServiceTwo")
    UserDetailsService userServiceTwo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        UserDetails user = userServiceTwo.loadUserByUsername(authentication.getName());

        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("Password Does Not Match");
        }

        String dbPass = user.getPassword();

        if (bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), dbPass)) {
            return new UsernamePasswordAuthenticationToken(user, authentication.getCredentials(), user.getAuthorities());
        }

        throw new AuthenticationCredentialsNotFoundException("Password Does Not Match");

    }

    public boolean supports(Class<? extends Object> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
