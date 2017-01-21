package security.util;

import database.models.UserEntity;
import database.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class JwtParseUtil {

    public static final String JWT_COOKIE = "jwt";

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Qualifier("userServiceTwo")
    UserDetailsService userServiceTwo;


    public UserDetails parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
            if (body.getId() == null) {
                return null;
            }

            UserEntity user = userRepository.findOne(body.getId());
            if (user == null)
                return null;
            return userServiceTwo.loadUserByUsername(user.getEmail());

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     *
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(UserEntity u) {
        Claims claims = Jwts.claims().setId(u.getUserId());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public void addAuthCookie(HttpServletRequest request, HttpServletResponse response, String authString) {
        removeCookie(request, response);
        if (request.getCookies() != null && request.getCookies().length > 0) {
            Cookie cookie = Arrays.asList(request.getCookies()).stream().filter(cookieStream -> JWT_COOKIE.equals(cookieStream.getName())).findFirst().orElse(new Cookie(JWT_COOKIE, authString));
            cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setValue(authString);
            cookie.setVersion(1);
            response.addCookie(cookie);

        } else {
            Cookie cookie = new Cookie(JWT_COOKIE, authString);
            cookie.setMaxAge(60 * 60 * 24 * 365 * 10);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setValue(authString);
            cookie.setVersion(1);
            response.addCookie(cookie);
        }

    }

    public void removeCookie(HttpServletRequest request, HttpServletResponse response) {
        if (request.getCookies() != null && request.getCookies().length > 0) {
            Arrays.asList(request.getCookies()).stream().filter(cookieStream -> JWT_COOKIE.equals(cookieStream.getName())).forEach(cookie -> {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setVersion(1);
                response.addCookie(cookie);
            });
        }

    }

    public String getTokenFromRequest(HttpServletRequest request) {
        if (request.getCookies() == null)
            return null;
        Cookie cookie = Arrays.asList(request.getCookies()).stream().filter(cookieStream -> JWT_COOKIE.equals(cookieStream.getName())).findFirst().orElse(null);
        if (cookie != null)
            return cookie.getValue();
        else
            return null;
    }
}