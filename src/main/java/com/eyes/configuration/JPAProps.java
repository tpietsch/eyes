package com.eyes.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Properties;


public class JPAProps extends Properties {

    Configuration configuration;

    public JPAProps(PropertiesConfiguration propertiesConfiguration) {
        this.configuration = propertiesConfiguration;
        setProperty("hibernate.connection.release_mode", "ON_CLOSE");
        setProperty("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION");
        if (configuration.getBoolean("hibernate.create-drop")) {
            setProperty("hibernate.hbm2ddl.auto","create-drop");
            setProperty("hibernate.hbm2ddl.import_files_sql_extractor","org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");
//            setProperty("hibernate.hbm2ddl.import_files", "impo");
        }
        setProperty("hibernate.hbm2ddl.auto","create-drop");
        setProperty("hibernate.dialect", configuration.getString("dialect", "org.hibernate.dialect.MySQLDialect"));
        setProperty("hibernate.show_sql", "true");
    }

}
