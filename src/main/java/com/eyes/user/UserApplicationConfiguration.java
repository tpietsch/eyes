package com.eyes.user;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by tpietsch on 1/22/17.
 */
@Configuration
@ComponentScan("com.eyes.user")
@EnableJpaRepositories("com.eyes.user.database.repositories")
public class UserApplicationConfiguration {
}
