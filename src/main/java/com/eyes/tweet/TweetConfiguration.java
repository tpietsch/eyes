package com.eyes.tweet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by tpietsch on 1/22/17.
 */
@Configuration
@ComponentScan("com.eyes.tweet")
@EnableJpaRepositories("com.eyes.tweet.database.repositories")
public class TweetConfiguration {
}
