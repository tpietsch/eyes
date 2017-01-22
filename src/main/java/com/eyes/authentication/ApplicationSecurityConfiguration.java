package com.eyes.authentication;

import com.eyes.authentication.rest.v1.AuthenticationRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by tpietsch on 1/22/17.
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.eyes.authentication")
@ImportResource("classpath:spring-security.xml")
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private EyesAuthenticationProvider eyesAuthenticationProvider;

    @Autowired
    private TokenAuthentication jwtAuthentication;

    @Autowired
    TokenAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    AuthenticationRest authenticationRest;

    public void configure(WebSecurity web) throws Exception {

    }

    protected void configure(HttpSecurity http) throws Exception {

    }

}
