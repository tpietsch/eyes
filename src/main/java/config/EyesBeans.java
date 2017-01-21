package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import database.JPAProps;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.Properties;

/**
 * Created by tpietsch on 1/21/17.
 */
@Configuration
@EnableWebMvc
public class EyesBeans extends WebMvcConfigurerAdapter {

    @Autowired
    ObjectMapper objectMapper;

    private final String configFile = "app-" + System.getProperty("env") + ".properties";

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("/index.html");
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
        super.configureMessageConverters(converters);
    }

    @Bean
    public PropertiesConfiguration getConfiguration() throws ConfigurationException {
        return new PropertiesConfiguration(configFile);
    }

    @Bean
    public PropertyPlaceholderConfigurer getPlaceholderConfig() throws ConfigurationException {
        PropertyPlaceholderConfigurer conf = new PropertyPlaceholderConfigurer();
        conf.setLocation(new ClassPathResource(configFile));
        return conf;
    }

    @Bean
    public CommonsMultipartResolver getFileUpdateResolver() {
        CommonsMultipartResolver x = new CommonsMultipartResolver();
        x.setMaxUploadSize(100000000);
        return x;
    }


    @Bean
    public MethodInvokingFactoryBean asd() {
        MethodInvokingFactoryBean x = new MethodInvokingFactoryBean();
        x.setTargetClass(SecurityContextHolder.class);
        x.setTargetMethod("setStrategyName");
        x.setArguments(new String[]{SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
        return x;
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

    /*
        <task:annotation-driven executor="myExecutor" scheduler="myScheduler"/>
    <task:executor id="myExecutor"  pool-size="5"/>
    <task:scheduler id="myScheduler" pool-size="10"/>


    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/files/images/*" />
            <bean id="responseCachingFilterImg" class="org.springframework.web.servlet.mvc.WebContentInterceptor">
                <property name="cacheSeconds" value="31556926"/>
            </bean>
        </mvc:interceptor>
        <mvc:interceptor>
            <mvc:mapping path="/**" />
            <mvc:mapping path="/*" />
            <mvc:exclude-mapping path="/files/images/*"/>
            <bean id="responseCachingFilter" class="org.springframework.web.servlet.mvc.WebContentInterceptor">
                <property name="cacheSeconds" value="0" />
            </bean>
        </mvc:interceptor>
    </mvc:interceptors>
     */

}
