package com.eyes.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

/**
 * Created by tpietsch on 1/22/17.
 */
@Configuration
@EnableWebSecurity
@ComponentScan("com.eyes.authentication")
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new EyesAuthenticationProvider();
    }

    @Bean
    public AuthenticationProvider authenticationProviderToken(){
        return new TokenAuthentication();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    @Qualifier("globalMethodSecurityConfiguration")
    public DefaultMethodSecurityExpressionHandler sd() {
        return new DefaultMethodSecurityExpressionHandler();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**")
                .and().ignoring().antMatchers("/")
                .and().ignoring().antMatchers("/index*")
                .and().ignoring().antMatchers("/rest/v1/register");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .enableSessionUrlRewriting(false).and()
                .cors()
                .and().anonymous()
                .and().csrf().disable()
                .addFilterAfter(tokenAuthenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/rest/v1/auth").permitAll()
                .and().authorizeRequests().antMatchers("/rest/**").fullyAuthenticated();

    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    @Autowired
    protected void configure(final AuthenticationManagerBuilder auth)
            throws Exception {
        auth.authenticationProvider(authenticationProvider());
        auth.authenticationProvider(authenticationProviderToken());
        super.configure(auth);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() throws Exception {
        return new TokenAuthenticationFilter(authenticationManagerBean());
    }

}
