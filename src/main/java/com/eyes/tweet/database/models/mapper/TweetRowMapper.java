package com.eyes.tweet.database.models.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eyes.tweet.database.models.TweetEntity;
import org.springframework.jdbc.core.RowMapper;

public class TweetRowMapper implements RowMapper
{
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        TweetEntity tweetEntity = new TweetEntity();
        tweetEntity.setUserId(rs.getString("user_id"));
        tweetEntity.setTweet(rs.getString("tweet"));
        tweetEntity.setTweetId(rs.getString("tweet_id"));
        tweetEntity.setDateCreated(rs.getTimestamp("date_created"));
        return tweetEntity;
    }

}