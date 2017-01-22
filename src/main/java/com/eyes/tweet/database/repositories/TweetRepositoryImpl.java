package com.eyes.tweet.database.repositories;


import com.eyes.tweet.database.models.TweetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

public class TweetRepositoryImpl{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TweetEntity save(TweetEntity tweetEntity) {

        String sql = "INSERT INTO tweet " +
                "(tweet_id,date_created,tweet,user_id) VALUES (?, ?, ?,?)";

        jdbcTemplate.update(sql, tweetEntity.getTweetId(),
                tweetEntity.getDateCreated(),
                tweetEntity.getTweet(),
                tweetEntity.getUserId());
        return tweetEntity;
    }
}