package rest.v1;


import database.models.UserEntity;
import database.repositories.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import security.authentication.EyesAuthenticationProvider;
import security.util.JwtParseUtil;
import security.util.CurrentUserUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static rest.RestEndpointConstants.V1;

@RestController
@RequestMapping(value = V1 + "/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationRest implements AuthenticationEntryPoint {
    private static Logger logger = Logger.getLogger(AuthenticationRest.class);

    @Autowired
    EyesAuthenticationProvider eyesAuthenticationProvider;

    @Autowired
    UserDetailsService loginUserDetails;

    @Autowired
    JwtParseUtil jwtParseUtil;

    @Qualifier("userRepository")
    @Autowired
    UserRepository userRepository;

    @Autowired
    CurrentUserUtil currentUserUtil;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public LoginStatus getStatus(HttpServletRequest request, HttpServletResponse response) {
        UserEntity userEntity = currentUserUtil.getCurrentUser();
        try {
            if (userEntity != null) {
                return new LoginStatus(true, userEntity.getEmail(),userEntity.getUserId());
            }
        } catch (Exception e) {
            return new LoginStatus(false, null,null);
        }
        return new LoginStatus(false, null,null);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
        jwtParseUtil.removeCookie(request, response);
        return ResponseEntity.ok(getStatus(request, response));
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody

    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody LoginCreds creds) {
        String username = creds.username;
        String password = creds.password;

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        try {
            token.setDetails(loginUserDetails.loadUserByUsername(username));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.ok(new LoginStatus(false, null,null));
        }
        try {
            Authentication auth = eyesAuthenticationProvider.authenticate(token);
            UserEntity userEntity = userRepository.findByEmail(username);
            String authString = jwtParseUtil.generateToken(userEntity);
            jwtParseUtil.addAuthCookie(request, response, authString);
            return ResponseEntity.ok(new LoginStatus(auth.isAuthenticated(), auth.getName(),userEntity.getUserId()));

        } catch (BadCredentialsException | AuthenticationCredentialsNotFoundException e) {
            return ResponseEntity.ok(new LoginStatus(false, null,null));
        }
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (authException != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }


    public static class LoginCreds {
        public String username;
        public String password;
    }


    public static class LoginStatus {
        public boolean loggedIn;
        public String username;
        public String userId;
        public LoginStatus(boolean loggedIn, String username,String userId) {
            this.loggedIn = loggedIn;
            this.username = username;
            this.userId = userId;
        }
    }

}


