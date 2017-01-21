package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import rest.v1.AuthenticationRest;
import security.authentication.EyesAuthenticationProvider;
import security.authentication.JwtAuthentication;
import security.authentication.JwtAuthenticationFilter;

/**
 * Created by tpietsch on 1/21/17.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class EyesSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private EyesAuthenticationProvider eyesAuthenticationProvider;

    @Autowired
    private JwtAuthentication jwtAuthentication;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    AuthenticationRest authenticationRest;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
       auth.authenticationProvider(eyesAuthenticationProvider).authenticationProvider(jwtAuthentication);
    }

    @Bean( name="authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**")
                .antMatchers("/files/images/**")
                .antMatchers("/rest/v1/register")
                .antMatchers("/");
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.addFilterAfter(jwtAuthenticationFilter, AnonymousAuthenticationFilter.class);
        http.authorizeRequests().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationRest);
        http.authorizeRequests().antMatchers("/rest/v1/auth").permitAll().antMatchers("/rest/**").fullyAuthenticated();


    }


}
