package com.eyes.follow;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by tpietsch on 1/22/17.
 */
@Configuration
@ComponentScan("com.eyes.follow")
@EnableJpaRepositories("com.eyes.follow.database.repositories")
public class FollowingConfiguration {
}
