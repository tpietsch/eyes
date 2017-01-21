package security.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import security.authentication.JwtAuthenticationToken;
import security.util.JwtParseUtil;
import security.util.CurrentUserUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter implements HandshakeInterceptor {

    @Autowired
    JwtParseUtil jwtParseUtil;

    @Autowired
    CurrentUserUtil currentUserUtil;

    @Autowired
    @Qualifier("userServiceTwo")
    UserDetailsService customUserService;

    public JwtAuthenticationFilter() {
        super("/**");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        String header = jwtParseUtil.getTokenFromRequest(request);
        if (StringUtils.isEmpty(header)) {
            return SecurityContextHolder.getContext().getAuthentication();
        }
        UserDetails parsedUser = jwtParseUtil.parseToken(header);
        if (parsedUser != null) {
            JwtAuthenticationToken authRequest = new JwtAuthenticationToken(header, parsedUser);
            return getAuthenticationManager().authenticate(authRequest);
        } else {
            return SecurityContextHolder.getContext().getAuthentication();
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        attemptAuthentication((HttpServletRequest) serverHttpRequest, (HttpServletResponse) serverHttpResponse);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}

