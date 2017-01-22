package com.eyes.tweet.database.repositories;


import com.eyes.tweet.database.models.TweetEntity;
import com.eyes.tweet.database.models.mapper.TweetRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

public class TweetRepositoryImpl  implements TweetRepositoryInterface{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TweetEntity saveTweet(TweetEntity tweetEntity) {

        String sql = "INSERT INTO tweet " +
                "(tweet_id,date_created,tweet,user_id) VALUES (?, ?, ?,?)";

        jdbcTemplate.update(sql, tweetEntity.getTweetId(),
                tweetEntity.getDateCreated(),
                tweetEntity.getTweet(),
                tweetEntity.getUserId());
        return tweetEntity;
    }

    @Override
    public List<TweetEntity> findByTweetSearchText(String userId, String searchTerm) {
        String SQL = "select * from tweet " +
                "where tweet.user_id=:userId and (:searchTerm is null or tweet.tweet like CONCAT('%',:searchTerm,'%'))";
        Map namedParameters = new HashMap();
        namedParameters.put("searchTerm", searchTerm);
        namedParameters.put("userId", userId);

        List<TweetEntity> tweets = namedParameterJdbcTemplate.query(
                SQL, namedParameters, new TweetRowMapper());

        return tweets;
    }

}