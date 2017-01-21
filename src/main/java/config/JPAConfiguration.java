package config;

import database.JPAProps;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories("database.repositories")
@EnableTransactionManagement
public class JPAConfiguration {

    @Autowired
    JPAProps jpaProps;

    @Autowired
    PropertiesConfiguration configuration;

    @Bean
    public DataSource dataSource() {
        //EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        //builder.generateUniqueName(true)
        // .setScriptEncoding("UTF-8")
        // .ignoreFailedDrops(true);
        // //.addScript("schema.sql");
        // //.addScripts("data.sql");
        //return builder.setType(EmbeddedDatabaseType.HSQL).build();
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

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setJpaProperties(jpaProps);
        factory.setPackagesToScan("database..models");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }
}