package com.eyes.configuration;

import com.eyes.authentication.ApplicationSecurityConfiguration;
import com.eyes.error.ErrorHandlingConfiguration;
import com.eyes.follow.FollowingConfiguration;
import com.eyes.registration.RegistrationConfiguration;
import com.eyes.tweet.TweetConfiguration;
import com.eyes.user.UserApplicationConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.configuration.*;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.lang.UsesJava7;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Properties;

/**
 * Created by tpietsch on 1/21/17.
 */
@Configuration
@Import({RegistrationConfiguration.class,
        TweetConfiguration.class,
        UserApplicationConfiguration.class,
        ErrorHandlingConfiguration.class,
        FollowingConfiguration.class,
        ApplicationSecurityConfiguration.class})
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan("com.eyes.configuration")
public class EyesRootConfiguration extends WebMvcConfigurerAdapter {

    private final String configFile = "app-" + System.getProperty("env") + ".properties";

    @Bean
    JsonObjectMapperProvider getJacksonObjectMapper() {
        return new JsonObjectMapperProvider();
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(new ByteArrayHttpMessageConverter());
        converters.add(new MappingJackson2HttpMessageConverter(getJacksonObjectMapper()));
        super.configureMessageConverters(converters);
    }

    @Bean(name = "viewResolver")
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/static/");
        registry
                .addResourceHandler("/","/index.html")
                .addResourceLocations("/index.html");
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

    @Bean
    public javax.sql.DataSource dataSource() throws ConfigurationException {
        PropertiesConfiguration configuration = getConfiguration();
        if(configuration.getBoolean("memory.db",false)){
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder.setType(EmbeddedDatabaseType.HSQL).build();
        }else{
            PoolProperties properties = new PoolProperties();
            properties.setUsername(configuration.getString("username"));
            properties.setPassword(configuration.getString("password"));
            properties.setDriverClassName(configuration.getString("driver"));
            properties.setUrl(configuration.getString("dburl"));
            properties.setMaxActive(10);
            properties.setMaxIdle(5);
            properties.setMinIdle(0);
            properties.setInitialSize(10);
            properties.setMaxWait(50000);
            properties.setValidationQuery(configuration.getString("validationquery"));
            properties.setTestOnBorrow(true);
            properties.setRemoveAbandoned(true);
            properties.setDefaultAutoCommit(false);
            return new org.apache.tomcat.jdbc.pool.DataSource(properties);
        }
    }

    @Bean
    public Properties getJpaProps() throws ConfigurationException {
        PropertiesConfiguration configuration = getConfiguration();
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.release_mode", "ON_CLOSE");
        properties.setProperty("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION");
        if (configuration.getBoolean("memory.db",false)) {
            properties.setProperty("hibernate.hbm2ddl.auto","create-drop");
            properties.setProperty("spring.jpa.hibernate.ddl-auto","create-drop");
            properties.setProperty("hibernate.hbm2ddl.import_files_sql_extractor","org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
            properties.setProperty("hibernate.hbm2ddl.import_files", "import.sql");
            properties.setProperty("hibernate.format_sql", "true");
        }else{
            properties.setProperty("hibernate.hbm2ddl.auto","create-drop");
        }
        properties.setProperty("hibernate.dialect", configuration.getString("dialect", "org.hibernate.dialect.MySQLDialect"));
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws ConfigurationException {
        org.apache.commons.configuration.Configuration configuration = getConfiguration();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl((configuration.getBoolean("memory.db",false)));
        vendorAdapter.setDatabasePlatform(configuration.getString("dialect", "org.hibernate.dialect.MySQLDialect"));
        vendorAdapter.setShowSql(true);


        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(getJpaProps());
        factory.setPackagesToScan("com.eyes");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws ConfigurationException {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() throws ConfigurationException {
        return  new JdbcTemplate(dataSource());
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
